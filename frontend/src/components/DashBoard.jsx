import axios from 'axios'
import React, { useEffect, useState } from 'react'

function DashBoard() {
    const [schedule,setSchedule] = useState([]);
    const [exam,setExam] = useState("");
    const[branch,setBranch]=useState("CSE");
    const [sem,setSem] = useState("1");
    useEffect(()=>{
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getschedule`,{params:{branch:branch,semester:sem}})
    .then(res=>{setSchedule(res.data);setExam(res.data[0].exam_type);})
    .catch(err=>console.log(err))
},[])
  return (
    <React.Fragment>
      {["CSE"].map((branch) => {
      const filteredSchedule = schedule.filter(item => item.branch === branch);
      return (
        <div key={branch}>
          <div><h1 align='center'>BRANCH : {branch}</h1></div>
          <table border={1} align='center' cellPadding={'10px'} style={{borderCollapse:'collapse'}}>
            <thead>
              <tr>
                <th colSpan={5} align='center'>{exam}</th>
              </tr>
              <tr>
                <th align='center'>SNO</th>
                <th align='center'>SUBJECT</th>
                <th align='center'>COURSE CODE</th>
                <th align='center'>DATE</th>
                <th align='center'>TIME</th>
              </tr>
            </thead>
            <tbody>
              {filteredSchedule.map((result, index) => (
                <tr key={result.id || index}>
                  <td>{index + 1}</td>
                  <td>{result.subject}</td>
                  <td>{result.coursecode}</td>
                  <td>{result.date}</td>
                  <td>{result.startTime}-{result.endTime}</td>
                </tr>
              ))}
            </tbody>
          </table>
      </div>
       );
      })}
    </React.Fragment>
  )
}

export default DashBoard;
