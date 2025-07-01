import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

function Student() {
  const navigate = useNavigate();
  const location = useLocation();
  let details = location.state?.details || null;
  const [exams, setExams] = useState(null);
  const [currentTime, setCurrentTime] = useState("");

  useEffect(() => {
    if (!details) {
      return;
    }

    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const year = String(today.getFullYear());
    const hours = String(today.getHours()).padStart(2, '0');
    const minutes = String(today.getMinutes()).padStart(2, '0');

    const formattedDate = `${day}-${month}-${year}`;
    const formattedTime = `${hours}:${minutes}`;
    setCurrentTime(formattedTime);

    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getexams`, {
      params: {
        branch: details.branch,
        semester: details.semester,
        date: formattedDate
      }
    })
    .then(res => {
      console.log(res.data);
      if (res.data.length > 0) {
        setExams(res.data[0]);
      } else {
        alert("No exams scheduled today.");
      }
    })
    .catch(err => {
      console.error(err);
      alert("Failed to fetch exams.");
    });
  }, [details]);


  const handleExamTime = (now, start, end) => {
    const [currH, currM] = now.split(':').map(Number);
    const [startH, startM] = start.split(':').map(Number);
    const [endH, endM] = end.split(':').map(Number);

    const currentMinutes = currH * 60 + currM;
    const startMinutes = startH * 60 + startM;
    const endMinutes = endH * 60 + endM;

    return currentMinutes >= startMinutes && currentMinutes <= endMinutes;
  };

  const handleSubmitOrNot = async (e) => {
    e.preventDefault();

    if (!details || !exams) {
      alert("Incomplete data to start exam.");
      return;
    }

    if (!exams.startTime || !exams.endTime) {
      alert("Exam timings not available.");
      return;
    }

    const allow = handleExamTime(currentTime, exams.startTime, exams.endTime);
    if (!allow) {
      alert(`Exam is only allowed between ${exams.startTime} and ${exams.endTime}`);
      return;
    }

    try {
      const res = await axios.get(`http://${import.meta.env.VITE_HOST}:8080/getresults`, {
        params: {
          batch: details.academic_year,
          branch: details.branch,
          coursecode: exams.coursecode,
          exam_type: exams.exam_type,
          semester: details.semester,
          section: details.section,
          username: details.username
        }
      });

      if (res.data !== null && Object.keys(res.data).length > 0) {
        alert("Already submitted.");
        return;
      }

      // Navigate to instructions
      navigate("/instructions", {
        state: {
          name: details.name,
          batch: details.academic_year,
          branch: details.branch,
          coursecode: exams.coursecode,
          examtype: exams.exam_type,
          semester: details.semester,
          section: details.section,
          username: details.username
        }
      });

    } catch (err) {
      console.error(err);
      alert("Already Submitted !");
    }
  };

  if (!details) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <h2 style={{ color: 'red' }}>ERROR: YOUR NOT AN AUTHORIZED PERSON.</h2>
      </div>
    );
  }

  return (
    <div style={{ display: 'flex', height: '100vh', width: '100vw' }}>
      <div style={{ border: '1px solid', width: '15%',fontWeight:'bold',display:'flex',flexDirection:'column',alignItems:'center',paddingTop:'5px' }}>
        WELCOME, {details?.name || "Student"}

        <div style={{paddingTop:'5px'}}><Link to="/" style={{textDecoration:'none',fontWeight:'bold'}}>LOGOUT</Link></div>
      </div>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', width: '85%' }}>
        <table border={1} align='center' style={{borderCollapse:'collapse',boxShadow:' 0 4px 8px rgba(0, 0, 0, 50)'}} cellPadding={'5px'}>
          <thead>
            <tr>
              <th>DATE</th>
              <th colSpan={2}>EXAM</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{exams?.date || "-"}</td>
              <td>{exams?.subject || "-"}</td>
              <td>
                <button
                  disabled={!exams}
                  onClick={handleSubmitOrNot}
                  className='button'
                >
                  START
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default Student;
