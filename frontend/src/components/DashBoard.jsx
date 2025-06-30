import axios from 'axios'
import React, { useEffect, useState } from 'react'

function DashBoard() {
    const [schedule,setSchedule] = useState([]);
    const [exam,setExam] = useState("");
    const[branch,setBranch]=useState("CSE");
    const [sem,setSem] = useState("1");
    useEffect(()=>{
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getschedule`,{params:{branch:branch,semester:sem}})
    .then(res=>{setSchedule(res.data);setExam(res.data[0].exam_type);console.log(res.data);})
    .catch(err=>console.log(err))
},[])
  return (
    <React.Fragment>
      {["CSE", "CST","CAI","AIM","ECE","ECT","MECH"].map((branch) => {
      const filteredSchedule = schedule.filter(item => item.branch === branch);
      return (
        <div key={branch}>
          <div><h1>BRANCH : {branch}</h1></div>
          <table border={1}>
            <thead>
              <tr>
                <td colSpan={5} align='center'>{exam}</td>
              </tr>
              <tr>
                <td>SNO</td>
                <td>SUBJECT</td>
                <td>COURSE CODE</td>
                <td>DATE</td>
                <td>TIME</td>
              </tr>
            </thead>
            <tbody>
              {filteredSchedule.map((result, index) => (
                <tr key={result.id || index}>
                  <td>{index + 1}</td>
                  <td>{result.subject}</td>
                  <td align='center'>{result.coursecode}</td>
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
