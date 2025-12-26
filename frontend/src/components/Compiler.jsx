import React, { useState, useRef, useCallback, useEffect } from 'react';
import Editor from '@monaco-editor/react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

const SAMPLES = {
  c: `#include <stdio.h>

int main() {
    printf("Hello, World!\\n");
    return 0;
}
`,
  cpp: `#include <iostream>
using namespace std;

int main() {
    cout << "Hello, World!" << endl;
    return 0;
}
`,
  python: `print("Hello, World!")`,
  java: `public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
`
};

function Compiler() {
  const navigate = useNavigate();
  const location = useLocation();
  const name = location.state?.name || null;
  const batch = location.state?.batch || null;
  const branch = location.state?.branch || null;
  const coursecode = location.state?.coursecode || null;
  const examtype = location.state?.examtype || null;
  const semester = location.state?.semester || null;
  const section = location.state?.section || null;
  const username = location.state?.username || null;
  const image = location.state?.image || null;
  const role = location.state?.role || null;
  let sess = location.state?.session || false;
  const token = location.state?.token || false;
  const [language, setLanguage] = useState('c');
  const [code, setCode] = useState(SAMPLES['c']);
  const [compilationOutput, setCompilationOutput] = useState('');
  const [stdinValue, setStdinValue] = useState('');
  const editorRef = useRef(null);
  const [questions, setQuestions] = useState([]); // [{ id, question_title, question_description }]
  const [selectedQuestionId, setSelectedQuestionId] = useState('');
  const [loadingQuestions, setLoadingQuestions] = useState(false);
  const [questionsError, setQuestionsError] = useState('');

  function htmlToText(html) {
    if (!html) return '';
    const div = document.createElement('div');
    div.innerHTML = html;
    return div.textContent || div.innerText || '';
  }

  const handleLanguageChange = (e) => {
    const lang = e.target.value;
    setLanguage(lang);
    setCode(SAMPLES[lang] || '');
    setCompilationOutput('');
  };

  const handleEditorMount = useCallback((editor) => {
    editorRef.current = editor;
  }, []);

  // fetch questions when component mounts (uses default semester/branch)
  useEffect(() => {
    const fetchQuestions = async () => {
      setLoadingQuestions(true);
      setQuestionsError('');
      setQuestions([]);
      setSelectedQuestionId('');
      try {
        const res = await axios.get(`http://${import.meta.env.VITE_HOST}:8081/student/getQuestions`, {
          params: { batch:batch, branch:branch, exam_type: examtype, coursecode:coursecode },
        });
        const data = Array.isArray(res.data) ? res.data : (res.data.questions || []);
        setQuestions(data || []);
        console.log(questions)
        // default select first question if available
        if (Array.isArray(data) && data.length > 0) {
          setSelectedQuestionId(data[0].id ?? data[0].question_id ?? data[0].questionTitle ?? '');
        }
      } catch (err) {
        setQuestionsError('Failed to load questions');
        console.error(err);
      } finally {
        setLoadingQuestions(false);
      }
    };

    fetchQuestions();
  }, [semester, branch]);

  const handleRun = async () => {
    setCompilationOutput('Submitting job...');
    try {
      const payload = {
        language,
        source_code: code,
        stdin: stdinValue || '',
        expectedoutput: ''
      };

      const submitRes = await axios.post(`http://${import.meta.env.VITE_HOST}:8081/api/submit`, payload, {
        headers: { 'Content-Type': 'application/json' }
      });

      const submitData = submitRes.data;
      const submissionId = submitData.submissionId || submitData.id || submitData.submissionID;

      if (!submissionId) {
        throw new Error('No submissionId returned from /api/submit');
      }

      setCompilationOutput('Waiting for result...');

      // Poll for result
      const timeoutMs = 30000;
      const pollInterval = 1000;
      const start = Date.now();
      let result = null;

      while (Date.now() - start < timeoutMs) {
        try {
          const res = await axios.get(`http://${import.meta.env.VITE_HOST}:8081/api/result/${encodeURIComponent(submissionId)}`);
          const data = res.data;
          result = data;
          if (data && (data.status === 'DONE' || (typeof data.status === 'string' && data.status.toUpperCase() === 'DONE'))) {
            break;
          }
        } catch (e) {
          // ignore and retry
        }
        await new Promise((r) => setTimeout(r, pollInterval));
      }

      if (!result) {
        throw new Error('Timed out waiting for result');
      }

      // Parse result.resultJson into object if it's a string
      let parsedResultJson = null;
      if (result.resultJson && typeof result.resultJson === 'string') {
        try {
          parsedResultJson = JSON.parse(result.resultJson);
        } catch (e) {
          parsedResultJson = { raw: result.resultJson, parseError: e.message };
        }
      } else {
        parsedResultJson = result.resultJson;
      }

      const display = {
        submissionId,
        id: result.id,
        language: result.language,
        sourceCode: result.sourceCode || result.source_code || code,
        stdin: result.stdin || '',
        status: result.status,
        expectedOutput: result.expectedOutput || result.expectedoutput || '',
        createdAt: result.createdAt,
        resultJsonParsed: parsedResultJson,
      };

      let outText = '';

      if (parsedResultJson && parsedResultJson.stdout != null && parsedResultJson.stdout !== '') {
        outText = parsedResultJson.stdout;
      } else if (parsedResultJson && parsedResultJson.compile_output != null && parsedResultJson.compile_output !== '') {
        outText = parsedResultJson.compile_output;
      } else if (parsedResultJson && parsedResultJson.stderr != null && parsedResultJson.stderr !== '') {
        outText = parsedResultJson.stderr;
      } else if (result && result.stdout != null && result.stdout !== '') {
        outText = result.stdout;
      } else if (result && result.compile_output != null && result.compile_output !== '') {
        outText = result.compile_output;
      } else if (result && result.stderr != null && result.stderr !== '') {
        outText = result.stderr;
      } else {
        outText = JSON.stringify(display, null, 2);
      }

      if (typeof outText !== 'string') {
        try {
          outText = String(outText);
        } catch (e) {
          outText = JSON.stringify(outText, null, 2);
        }
      }

      setCompilationOutput(outText);
    } catch (err) {
      setCompilationOutput('Run failed: ' + String(err));
    }
  };

  const handleSubmit = async () => {
    if (!selectedQuestion) {
      alert("Please select a question before submitting.");
      return;
    }

    setCompilationOutput('Submitting your code...');

    const body = {
      //id: selectedQuestion.id || selectedQuestion.question_id,
      batch: batch,
      branch: branch,
      semester: semester,
      coursecode: coursecode,
      examType: examtype,
      section: section,
      username: username,
      question_title: selectedQuestion.question_title,
      source_code: code,
      marks: 0, // Assuming marks are part of the question object
    };

    try {
      const response = await axios.post(`http://${import.meta.env.VITE_HOST}:8081/student/submitCode`, body, {
        headers: { 'Content-Type': 'application/json' }
      });

      if (response.status === 200) {
        alert('Code submitted successfully!');
        const details = location.state;
        // navigate("/student", { state: { details, token } });
      }
    } catch (err) {
      console.error(err);
      let errorMessage = 'Failed to submit code.';
      if (err.response) {
        errorMessage += ` Server responded with ${err.response.status}: ${err.response.data}`;
      } else if (err.request) {
        errorMessage += ' No response from server.';
      } else {
        errorMessage += ` ${err.message}`;
      }
      setCompilationOutput(errorMessage);
      alert(errorMessage);
    }
  };

  const selectedQuestion = questions.find(q => {
    if (!selectedQuestionId) return false;
    return q.id === selectedQuestionId || q.question_id === selectedQuestionId || q.questionTitle === selectedQuestionId;
  }) || {};

  // helper to render description (show only question_description if present,
  // otherwise show only the first test case + expected output).
  // ALWAYS show only ONE example (first).
  const renderDescription = (q) => {
    const desc = (q.question_description ?? q.question_descrption ?? q.description ?? '').toString().trim();

    const inputs = Array.isArray(q.testCases) ? q.testCases : (q.testCases || []);
    const outputs = Array.isArray(q.testCasesOutput) ? q.testCasesOutput : (q.testCasesOutput || []);
    const firstInput = inputs[0] ?? '';
    const firstOutput = outputs[0] ?? '';

    return (
      <div style={{ color: '#333', whiteSpace: 'pre-wrap' }}>
        {desc ? (
          <>
            <div style={{ marginBottom: 10 }}>{htmlToText(desc)}</div>
            {(firstInput !== '' || firstOutput !== '') && (
              <div style={{ marginTop: 8 }}>
                <strong style={{ display: 'block', marginBottom: 6 }}>Example Input:</strong>
                <div style={{ fontFamily: 'monospace', background: '#f7f7f7', padding: 8, borderRadius: 4 }}>{String(firstInput)}</div>
                {firstOutput !== '' && (
                  <>
                    <strong style={{ display: 'block', marginTop: 8, marginBottom: 6 }}>Expected Output:</strong>
                    <div style={{ fontFamily: 'monospace', background: '#f7f7f7', padding: 8, borderRadius: 4 }}>{String(firstOutput)}</div>
                  </>
                )}
              </div>
            )}
          </>
        ) : (
          <>
            {firstInput !== '' ? (
              <>
                <div style={{ marginBottom: 8 }}>
                  <strong>Example Input:</strong>
                  <div style={{ fontFamily: 'monospace', background: '#f7f7f7', padding: 8, borderRadius: 4, marginTop: 6 }}>{String(firstInput)}</div>
                </div>
                {firstOutput !== '' && (
                  <div>
                    <strong>Expected Output:</strong>
                    <div style={{ fontFamily: 'monospace', background: '#f7f7f7', padding: 8, borderRadius: 4, marginTop: 6 }}>{String(firstOutput)}</div>
                  </div>
                )}
              </>
            ) : (
              <div style={{ color: '#666' }}>No description provided.</div>
            )}
          </>
        )}
      </div>
    );
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 8, padding: 12, height: '100vh', width: '100vw', boxSizing: 'border-box' }}>
      <header style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
        <h2 style={{ margin: 0 }}>SVEC</h2>

        <div style={{ marginLeft: 'auto', display: 'flex', gap: 8 }}>
          <button onClick={handleRun}>Run</button>
          <button onClick={handleSubmit}>Submit</button>
        </div>
      </header>

      <main style={{ display: 'flex', gap: 12, marginTop: 8, flex: 1, minHeight: 0 }}>
        {/* Left: Questions + Standard Input & Compilation Output (same width) */}
        <aside style={{ width: '45%', display: 'flex', flexDirection: 'column', gap: 12, flex: 1, minHeight: 0 }}>
          {/* Questions label and dropdown on same line */}
          <div style={{ padding: 8 }}>
            {loadingQuestions ? (
              <div>Loading questions...</div>
            ) : questionsError ? (
              <div style={{ color: 'red' }}>{questionsError}</div>
            ) : (
              <label style={{ display: 'flex', gap: 12, alignItems: 'center' }}>
                <span style={{ minWidth: 90, fontWeight: 600 }}>Question</span>
                <select
                  value={selectedQuestionId}
                  onChange={(e) => setSelectedQuestionId(e.target.value)}
                  style={{ flex: 1, padding: 8 }}
                >
                  {questions.map((q, idx) => (
                    <option key={q.id ?? idx} value={q.id ?? q.question_id ?? q.questionTitle ?? idx}>
                      {q.question_title ?? q.title ?? q.questionTitle ?? `Question ${idx + 1}`}
                    </option>
                  ))}
                </select>
              </label>
            )}
          </div>

          {/* Description block - labelled "Description" (do not re-show title in bold) */}
          <div style={{ padding: 12, border: '1px solid #ddd', borderRadius: 4, minHeight: 0, background: '#fff', maxHeight: 160, overflow: 'auto' }}>
            <h3 style={{ marginTop: 0, marginBottom: 12 }}>Description</h3>
            {selectedQuestion ? renderDescription(selectedQuestion) : <div style={{ color: '#666' }}>Select a question to view its description.</div>}
          </div>

          <div style={{ flex: 1, padding: 8, border: '1px solid #ddd', borderRadius: 4, minHeight: 0, display: 'flex', flexDirection: 'column', overflow: 'hidden', maxHeight: 220 }}>
            <h4 style={{ margin: '0 0 8px 0' }}>Standard Input</h4>
            <div style={{ flex: 1, minHeight: 0, overflow: 'auto' }}>
              <textarea
                value={stdinValue}
                onChange={(e) => setStdinValue(e.target.value)}
                placeholder="Enter input for the program (stdin)"
                style={{ width: '100%', height: '100%', boxSizing: 'border-box', fontFamily: 'monospace', padding: 8, resize: 'vertical' }}
              />
            </div>
          </div>

          <div style={{ flex: 1, padding: 8, border: '1px solid #ddd', borderRadius: 4, minHeight: 0, overflow: 'auto', display: 'flex', flexDirection: 'column', maxHeight: 220 }}>
            <h4 style={{ margin: '0 0 8px 0' }}>Compilation Output</h4>
            <div style={{ overflow: 'auto', flex: 1 }}>
              <pre style={{ whiteSpace: 'pre-wrap', margin: 0 }}>{compilationOutput}</pre>
            </div>
          </div>
        </aside>

        {/* Right: Editor */}
        <div style={{ width: '55%', border: '1px solid #ddd', flex: 1, minHeight: 0, display: 'flex', flexDirection: 'column', overflow: 'hidden' }}>
          {/* moved Language + Load sample here above the editor */}
          <div style={{ padding: '8px 12px', borderBottom: '1px solid #eee', display: 'flex', gap: 12, alignItems: 'center' }}>
            <label style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
              Language:
              <select value={language} onChange={handleLanguageChange} style={{ marginLeft: 4 }}>
                <option value="c">C</option>
                <option value="cpp">C++</option>
                <option value="python">Python</option>
                <option value="java">Java</option>
              </select>
            </label>

            <button onClick={() => { setCode(SAMPLES[language]); setCompilationOutput(''); }}>
              Load sample
            </button>
          </div>

          <div style={{ flex: 1, minHeight: 0 }}>
            <Editor
              height="100%"
              defaultLanguage={language === 'cpp' ? 'cpp' : language}
              language={language === 'cpp' ? 'cpp' : language}
              value={code}
              onChange={(value) => setCode(value ?? '')}
              onMount={handleEditorMount}
              options={{ minimap: { enabled: false }, fontSize: 14 }}
            />
          </div>
        </div>
      </main>
    </div>
  );
}

export default Compiler;