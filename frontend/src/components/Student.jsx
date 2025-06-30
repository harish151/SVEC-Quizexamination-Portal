import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

function Student() {
  const navigate = useNavigate();
const location = useLocation();
const details = location.state?.details || null;
const [exams,setExams] = useState([]);
useEffect(()=>{
  const today = new Date();
  const day = String(today.getDate()).padStart(2,'0');
  const month = String(today.getMonth()+1).padStart(2,'0');
  const year = String(today.getFullYear());
  // const hours = String(today.getHours()).padStart(2, '0');
  // const minutes = String(today.getMinutes()).padStart(2, '0');
  const formattedDate = `${day}-${month}-${year}`;
  // const formattedTime = `${hours}:${minutes}`;
  console.log(details);
  axios.get(`http://${import.meta.env.VITE_HOST}:8080/getexams`,{params:{branch:details.branch,semester:details.semester,date:formattedDate}})
  .then(res =>{console.log(res.data);setExams(res.data[0])})
  .catch(err => alert(err));
},[])

  return (
    <div style={{display:'flex',height:'100vh',width:'100vw'}}>
        <div style={{border:'1px solid '}}>Welcome {details.name}</div>
        <div style={{display:'flex',alignItems:'center',justifyContent:'center',width:'100%'}}>
          {/* <div><span>{exams.exam_type}</span><br /><span>{exams.subject}</span></div> */}
          <table border={1} align='center'>
            <thead>
              <tr>
                <td>DATE</td>
                <td>EXAM</td>
                <td></td>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>{exams.date}</td>
                <td>{exams.subject}</td>
                <td><button onClick={(e)=>{e.preventDefault();navigate("/instructions",{state:{batch:details.academic_year,branch:details.branch,coursecode:exams.coursecode,examtype:exams.exam_type,semester:details.semester,section:details.section,username:details.username}})}}>START</button></td>
              </tr>
            </tbody>
          </table>
        </div>
    </div>
  )
}

export default Student
