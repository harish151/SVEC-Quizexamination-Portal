import React from 'react'
import { useState,useEffect } from 'react';
import axios from 'axios';
import FormComponent from './Form';

function VerifyCodeResult({token}) {

    const [regulation, setRegulation] = useState();
    const [batch, setBatch] = useState(-1);
    const [branch, setBranch] = useState(-1);
    const [semester, setSemester] = useState(-1);
    const [subjects, setSubjects] = useState({});
    const [sections,setSections] = useState([-1,"A","B","C","D"]);
    const[selectedsec,setSelectedsec] = useState(-1);
    const [ccode,setCcode] = useState("");
    const [exam_type,setExam_type] = useState(-1);
    const [student,setStudent] = useState([-1]);
    const [selectedstudent,setSelectedstudent] = useState(-1);
    const [result,setResult] = useState([]);
    const [buttonname,setButtonname] = useState("View code Result");
    const [displayres,setDisplayres] = useState(true);
    const [display,setDisplay] = useState(0);
    const [subjectText, setSubjectText] = useState();

    const handleregulation = (selectedBatch,selectedbranch) => {
        if (selectedBatch === -1 || selectedbranch === -1) return;
        axios.get(`http://${import.meta.env.VITE_HOST}:8080/teacher/getregulation`, {
        headers:{Authorization:token},
        withCredentials: true,
        params: { batch: selectedBatch, branch:selectedbranch  }
        })
        .then(res => {
        setRegulation(res.data[0].regulation);
        setSections(res.data[0].sections);
        })
        .catch(err => alert(err));
  };

  const liststudents = (batch,branch,semester,section)=>{
    if (batch === -1 || branch === -1 || semester === -1 || section === undefined) return;
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/teacher/getstudents`, {
        headers:{Authorization:token},
        withCredentials: true,
        params: { batch: batch, branch:branch , semester:semester, section:section }
        })
        .then(res => {
        setStudent(res.data);
        })
        .catch(err => alert(err));
  };

  const handleresult =async (e)=>{
    e.preventDefault();
    //console.log(batch,branch,semester,selectedsec,ccode,exam_type,selectedstudent);
    try {
      axios.get(`http://${import.meta.env.VITE_HOST}:8081/faculty/viewcodingresultsbyuser`, {
        //headers:{Authorization:token},
        //withCredentials: true,
        params: {
          batch: batch,
          branch: branch,
          coursecode: ccode,
          exam_type: exam_type,
          semester: semester,
          section: selectedsec,
          username: selectedstudent
        },
      })
        .then(res => {
          console.log(res.data);})
        .catch(err => alert(err));
    } catch (error) {
      console.error(error);
    }

      
  }

  useEffect(() => {
    if(batch === -1 || branch === -1 || semester === -1) return;
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/teacher/getsubjects`, {
      headers:{Authorization:token},
      withCredentials: true,
      params: {
        regulation: regulation,
        branch: branch,
        semester: semester
      }
    })
    .then(res => {
      setSubjects(res.data[0]);
      setCcode("-1");
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
        setDisplay={setDisplay}
        buttonname = {buttonname}
        setButtonname = {setButtonname}
        handleregulation={handleregulation}
        setSubjectText={setSubjectText}
        listofstu={student}
        setSelectedstudent={setSelectedstudent}
        selectedstudent={selectedstudent}
        liststudents={liststudents}
        handlequestions={handleresult}
       />
    </div>
  )
}

export default VerifyCodeResult
