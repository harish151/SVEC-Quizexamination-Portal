import React, { useState } from 'react';
import axios from 'axios';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; // import styles

function CodingQuestions(props) {
  
  const [batch, setBatch] = useState(props.batch || '');
  const [branch, setBranch] = useState(props.branch || '');
  const [examType, setExamType] = useState(props.exam_type || '');
  const [semester, setSemester] = useState(props.semester || '');
  const [courseCode, setCourseCode] = useState(props.coursecode ||'');
  const [questionNo, setQuestionNo] = useState('');
  const [questionTitle, setQuestionTitle] = useState('');
  const [marks, setMarks] = useState('');
  const [description, setDescription] = useState('');
  const [testCases, setTestCases] = useState([{ input: '', output: '' }]);

  const handleTestCaseChange = (index, field, value) => {
    const updatedTestCases = [...testCases];
    updatedTestCases[index][field] = value;
    setTestCases(updatedTestCases);
  };

  const addTestCase = () => {
    setTestCases([...testCases, { input: '', output: '' }]);
  };

  const removeTestCase = (index) => {
    const updatedTestCases = [...testCases];
    updatedTestCases.splice(index, 1);
    setTestCases(updatedTestCases);
  };

  const validateForm = () => {
    if (!questionNo || !questionTitle) {
      alert('Please provide question number and title.');
      return false;
    }
    if (!marks || Number(marks) <= 0) {
      alert('Please provide valid marks for the question.');
      return false;
    }
    if (!description || description.trim() === '' || description === '<p><br></p>') {
      alert('Question description is required.');
      return false;
    }
    for (let i = 0; i < testCases.length; i++) {
      if (!testCases[i].input || !testCases[i].output) {
        alert(`Please fill input and output for test case #${i + 1}.`);
        return false;
      }
    }
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    const body = {
      batch,
      exam_type: examType,
      branch,
      semester,
      coursecode: courseCode,
      question_no: questionNo,
      question_title: questionTitle,
      question_description: description,
      testCases: testCases.map(tc => tc.input),
      testCasesOutput: testCases.map(tc => tc.output),
      marks: parseFloat(marks),
    };

    try {
      const response = await axios.post(`http://${import.meta.env.VITE_HOST}:8081/faculty/create`, body);

      if (response.status === 200) {
        alert('Coding question saved!');

        // optional: clear form after success
        setQuestionTitle('');
        setQuestionNo('');
        setDescription('');
        setTestCases([{ input: '', output: '' }]);
        setMarks('');
      }
    } catch (err) {
      console.error(err);
      let errorMessage = 'Failed to save question.';
      if (err.response) {
        // The request was made and the server responded with a status code
        // that falls out of the range of 2xx
        errorMessage += ` Server responded with ${err.response.status}: ${err.response.data}`;
      } else if (err.request) {
        // The request was made but no response was received
        errorMessage += ' No response from server.';
      } else {
        // Something happened in setting up the request that triggered an Error
        errorMessage += ` ${err.message}`;
      }
      alert(errorMessage);
    }
  };

  // Configuration for the editor's toolbar
  const modules = {
    toolbar: [
      [{ header: '1' }, { header: '2' }, { font: [] }],
      [{ size: [] }],
      ['bold', 'italic', 'underline', 'strike', 'blockquote', 'code-block'],
      [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
      ['link', 'image', 'video'],
      ['clean'],
    ],
  };

  return (
    <div className="container mt-4">
      <h2>Add New Coding Question</h2>
      <form onSubmit={handleSubmit}>
        <div className="row mb-3">
          <div className="col-md-2">
            <label className="form-label">Question No.</label>
            <input
              name="questionNo"
              type="number"
              min="1"
              className="form-control"
              value={questionNo}
              onChange={(e) => setQuestionNo(e.target.value)}
              required
            />
          </div>

          <div className="col-md-8">
            <label className="form-label">Question Title</label>
            <input
              name="questionTitle"
              type="text"
              className="form-control"
              value={questionTitle}
              onChange={(e) => setQuestionTitle(e.target.value)}
              placeholder={questionNo ? `Question ${questionNo}: Title` : 'Question Title'}
              required
            />
          </div>

          <div className="col-md-2">
            <label className="form-label">Marks</label>
            <input
              name="marks"
              type="number"
              min="0"
              className="form-control"
              value={marks}
              onChange={(e) => setMarks(e.target.value)}
              required
            />
          </div>
        </div>

        {/* Preview header: shows "Question X: Title" above the description */}
        <div className="mb-2">
          <h5>
            {questionNo ? `Question ${questionNo}` : 'Question'}:
            {' '}
            {questionTitle ? questionTitle : '(No title yet)'}
          </h5>
        </div>

        <div className="mb-3">
          <label className="form-label">
            Question Description (HTML will be sent)
          </label>
          <ReactQuill
            theme="snow"
            value={description}
            onChange={setDescription}
            modules={modules}
            placeholder="Enter the question description. You can use the toolbar for formatting and to add images."
            style={{ height: '300px', marginBottom: '50px' }}
          />
        </div>

        <hr />

        <h4>Test Cases</h4>
        {testCases.map((testCase, index) => (
          <div key={index} className="row mb-3 p-3 border rounded">
            <div className="col-md-5">
              <label htmlFor={`test-case-input-${index}`} className="form-label">
                Test Case #{index + 1} - Input
              </label>
              <textarea
                id={`test-case-input-${index}`}
                name={`testcase_input_${index}`}
                className="form-control"
                rows="3"
                value={testCase.input}
                onChange={(e) => handleTestCaseChange(index, 'input', e.target.value)}
                placeholder="Input for the test case"
                required
              />
            </div>
            <div className="col-md-5">
              <label htmlFor={`test-case-output-${index}`} className="form-label">
                Expected Output
              </label>
              <textarea
                id={`test-case-output-${index}`}
                name={`testcase_output_${index}`}
                className="form-control"
                rows="3"
                value={testCase.output}
                onChange={(e) => handleTestCaseChange(index, 'output', e.target.value)}
                placeholder="Expected output for the test case"
                required
              />
            </div>
            <div className="col-md-2 d-flex align-items-end">
              <button
                type="button"
                className="btn btn-danger"
                onClick={() => removeTestCase(index)}
                disabled={testCases.length === 1}
              >
                Remove
              </button>
            </div>
          </div>
        ))},

        <div className="mb-3">
          <button
            type="button"
            className="btn btn-secondary"
            onClick={addTestCase}
          >
            Add Test Case
          </button>
        </div>

        <hr />

        <button type="submit" className="btn btn-primary">
          Save Question
        </button>
      </form>
    </div>
  );
}

export default CodingQuestions;