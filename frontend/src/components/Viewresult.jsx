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
    const [ccode,setCcode] = useState("");
    const [exam_type,setExam_type] = useState("MID-1");
    const [displayres,setDisplayres] = useState(0);
    const [buttonname,setButtonname] = useState("View Result");

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
    setDisplayres(1);

    
  }


    useEffect(() => {
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
        setDisplay={setDisplayres}
        buttonname = {buttonname}
        setButtonname = {setButtonname}
        handleregulation={handleregulation}
        handlequestions={handleresult}
       />
       {displayres ===1 ? (<h1>result</h1>):(null)}
    </div>
  )
}

export default Viewresult;
