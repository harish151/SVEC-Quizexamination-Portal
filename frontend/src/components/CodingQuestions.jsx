import React, { useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css'; // import styles

function CodingQuestions() {
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

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log({
      description, // This is now HTML
      testCases,
    });
    alert('Coding question saved! Check the console for the HTML content.');
  };

  // Configuration for the editor's toolbar
  const modules = {
    toolbar: [
      [{ 'header': '1'}, {'header': '2'}, { 'font': [] }],
      [{size: []}],
      ['bold', 'italic', 'underline', 'strike', 'blockquote', 'code-block'],
      [{'list': 'ordered'}, {'list': 'bullet'}, 
       {'indent': '-1'}, {'indent': '+1'}],
      ['link', 'image', 'video'],
      ['clean']
    ],
  };

  return (
    <div className="container mt-4">
      <h2>Add New Coding Question</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">
            Question Description
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
              >
                Remove
              </button>
            </div>
          </div>
        ))}

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
