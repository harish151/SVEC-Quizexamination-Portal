import React from 'react'
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
function Instructions() {
    const navigate = useNavigate();
    const location = useLocation();
    const batch = location.state?.batch || null;
    const branch = location.state?.branch || null;
    const coursecode = location.state?.coursecode || null;
    const exam_type = location.state?.examtype || null;
    const semester = location.state?.semester || null;
    const section = location.state?.section || null;
    const username = location.state?.username || null;

    console.log(batch,branch,coursecode,exam_type);

    const handleexam =(e)=>{
        e.preventDefault();
        navigate("/exam",{state:{batch:batch,branch:branch,coursecode:coursecode,examtype:exam_type,semester:semester,section:section,username:username}});
        goFullscreen();
    }
    const goFullscreen = () => {
        const elem = document.documentElement;
        if (elem.requestFullscreen) {
        elem.requestFullscreen().catch((err) =>
            console.error('Fullscreen error:', err)
        );
        } else if (elem.webkitRequestFullscreen) {
        elem.webkitRequestFullscreen();
        } else if (elem.msRequestFullscreen) {
        elem.msRequestFullscreen();
        }
    };

  return (
    <div>
      <h1>INSTRUCTIONS</h1>
      <div>

      </div>
      <form onSubmit={handleexam}>
      <ul>
        <li>The Duration of the contest is 20 Minutes.</li>
        <li>There are a total of 20 questions, and 1/2 marks are awarded for every correct response.</li>
        <li>There are four options for each MCQ out of which only one will be correct.</li>
        <li>Your response will be recorded only when you submit your response for each question so make sure you submit your response within the time limit.</li>
        <li>Please submit a response to an MCQ once you are sure, as you cannot change it once submitted.</li>
        <li>The maximum mark for the contest is 10.</li>
      </ul>
      <h1>QUIZ DETAILS</h1>
      <ul>
        <li>You can modify your answers any number of times during the contest.</li>
        <li>If you finished your exam then please submit the exam. If time limit has reached then your answers will be submitted automatically.</li>
        <li>All the best for your exam.</li>
      </ul>
      <input type="checkbox" name="checkbox" id="checkbox" style={{width:'18px',height:'18px'}} required/>
      <label htmlFor="checkbox">Mark as if you read all the instructions mentioned above.</label>
      <center>
        <button type="submit" style={{marginTop:'50px'}}>START</button>
      </center>
      </form>
    </div>
  )
}

export default Instructions
