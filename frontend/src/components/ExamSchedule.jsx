import React from 'react'
import { useState,useEffect } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

function ExamSchedule(props) {
  const location = useLocation();
  const details = location.state?.details || null;
  const [schedule,setSchedule] = useState([]);
  const [exam,setExam] = useState("");
  const[branch]=useState(props.branch);
  const [result,setResult]= useState([])
  useEffect(() => {
      const fetchSchedules = async () => {
        const listsemesters = details.role.toLowerCase() === "teacher"? ["I", "III"]: [details.semester];
        try {
          const responses = await Promise.all(
            listsemesters.map((semester) =>
              axios.get(`http://${import.meta.env.VITE_HOST}:8080/getschedule`, {
                params: { branch: branch, semester: semester },
              })
            )
          );
          const allData = responses.map((res) => res.data);
          setResult(allData);
          setSchedule(allData.flat());
          if (allData[0]?.length > 0) {
            setExam(allData[0][0].exam_type);
          }
        } catch (error) {
          console.error("Error fetching schedules:", error);
        }
      };
      fetchSchedules();
    }, []);
  return (
    <React.Fragment>
        <div key={branch} className='w-100'>
            {schedule.length > 0 && (result.map((items,index)=>(
           <table key={index} className='table-bordered ms-5 mt-4 w-75 p-0'>
              <thead style={{ backgroundColor: 'gray' }}>
                <tr>
                  <th colSpan={5} align='center'>
                    {items[0]?.semester || 'N/A'} SEMESTER {`(${exam})`}
                  </th>
                </tr>
                <tr>
                  <th><center>SNO</center></th>
                  <th><center>SUBJECT</center></th>
                  <th><center>COURSE CODE</center></th>
                  <th><center>DATE</center></th>
                  <th><center>TIME</center></th>
                </tr>
              </thead>
              <tbody>
                {items.map((item, rowIndex) => (
                  <tr key={item.id || rowIndex}>
                    <td align='center'>{rowIndex+1}</td>
                    <td align='left' width={'fit-content'}>{item.subject}</td>
                    <td align='center'>{item.coursecode}</td>
                    <td align='center'>{item.date}</td>
                    <td align='center'>{item.startTime} - {item.endTime}</td>
                  </tr>
                ))}
              </tbody>
            </table>))
          )}
      </div>
    </React.Fragment>
  )
}

export default ExamSchedule
