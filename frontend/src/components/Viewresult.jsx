import React from 'react'
import { useState,useEffect } from 'react';
import axios from 'axios';
import FormComponent from './Form';

function Viewresult() {

    const [regulation, setRegulation] = useState("V20");
    const [batch, setBatch] = useState("2021");
    const [branch, setBranch] = useState("CSE");
    const [semester, setSemester] = useState("1");
    const [subjects, setSubjects] = useState({});
    const [sections,setSections] = useState(["A","B","C","D"]);
    const[selectedsec,setSelectedsec] = useState("A");
    const [ccode,setCcode] = useState("");
    const [exam_type,setExam_type] = useState("MID-1");
    const [result,setResult] = useState([]);
    const [buttonname,setButtonname] = useState("View Result");
    const [displayres,setDisplayres] = useState(true);

    const handleregulation = (selectedBatch,selectedbranch) => {
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getregulation`, {
      params: { batch: selectedBatch, branch:selectedbranch }
    })
    .then(res => {
      console.log(res.data);
      setRegulation(res.data[0].regulation);
      setSections(res.data[0].sections);
    })
    .catch(err => alert(err));
  };

  const handleresult =(e)=>{
    e.preventDefault();
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getresultswithoutusername`,{params:{batch:batch,branch:branch,coursecode:ccode,exam_type:exam_type,semester:semester,section:selectedsec}})
    .then(res=>{setResult(res.data);if(res.data.length==0){setDisplayres(1);}})
    .catch(err=>{alert(err);})
  }


    useEffect(() => {
    console.log(selectedsec);
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getsubjects`, {
      params: {
        regulation: regulation,
        branch: branch,
        semester: semester
      }
    })
    .then(res => {
      setSubjects(res.data[0]);
      setCcode(res.data[0].coursecode[0]);
    })
    .catch(err => alert(err));
  }, [branch, regulation, semester]);


  return (
    <div>
      <FormComponent
        batch={batch}
        setBatch={setBatch}
        branch={branch}
        setBranch={setBranch}
        semester={semester}
        setSemester={setSemester}
        subjects={subjects}
        ccode={ccode}
        setCcode={setCcode}
        exam_type={exam_type}
        setExam_type={setExam_type}
        sections={sections}
        setSections={setSections}
        selectedsec = {selectedsec}
        setSelectedsec = {setSelectedsec}
        setDisplay={0}
        buttonname = {buttonname}
        setButtonname = {setButtonname}
        handleregulation={handleregulation}
        handlequestions={handleresult}
       />
      {Array.isArray(result) && result.length > 0 && (
        <table border="1" style={{ marginTop: '20px', width: '100%',borderCollapse:'collapse' }}>
          <thead>
            <tr>
              <th>SNO</th>
              <th>USERNAME</th>
              <th>MARKS</th>
            </tr>
          </thead>
          <tbody>
            {result.map((res, index) => (
              <tr key={res.username + index}>
                <td align='center'>{index + 1}</td>
                <td align='center'>{res.username}</td>
                <td align='center'>{res.marks}</td>
              </tr>
            ))}
          </tbody>
        </table>
        )}

        {result.length === 0 && displayres===1 && ("NO RESULT")}


    </div>
  )
}
export default Viewresult;
