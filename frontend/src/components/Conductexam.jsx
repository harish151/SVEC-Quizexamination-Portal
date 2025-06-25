import React, { useEffect, useState } from 'react';
import axios from 'axios';

function Conductexam() {
  const [regulation, setRegulation] = useState("V20");
  const [batch, setBatch] = useState("2021");
  const [branch, setBranch] = useState("CSE");
  const [semester, setSemester] = useState("1");
  const [subjects, setSubjects] = useState({});
  const [ccode,setCcode] = useState("");
  const [exam_type,setExam_type] = useState("MID-1");
  const [displayque,setDisplayque] = useState(0);
  const [question,setQuestion] = useState("");
  const [options, setOptions] = useState(["", "", "", ""]);
  const [answer,setAnswer]=useState("");
  const [qno,setQno] = useState(1);

  const handleregulation = (selectedBatch) => {
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getregulation`, {
      params: { batch: selectedBatch }
    })
    .then(res => {
      setRegulation(res.data);
    })
    .catch(err => alert(err));
  };

  const handleaddquestions =(e)=>{
    e.preventDefault();
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getnumofqueposted`,{params:{batch:batch,exam_type:exam_type,branch:branch,coursecode:ccode}})
    .then(res =>{
      if(res.data===20){
        alert("Already Question are Assigned.");
      }
      else{
        setDisplayque(1);
        setQno(res.data+1);console.log(res.data)
      }
    })
    .catch(err => alert(err))
    
  }

  const handlequestion =(e)=>{
    e.preventDefault();
    axios.post(`http://${import.meta.env.VITE_HOST}:8080/addquestions`,{batch:batch,exam_type:exam_type,branch:branch,semester:semester,coursecode:ccode,question_no:qno,question:question,options:options,answer:answer})
    .then(res => {console.log(res.data);console.log(ccode);setQno(qno+1);
        setQuestion("");
        setOptions(["","","",""]);
        setAnswer("");
    })
    .catch(err => console.error(err))

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


  var divs = [];
  if(qno<=20){
    divs.push(<form key={qno} id="que-form" onSubmit={handlequestion}><div style={{display:'flex',flexDirection:'column',justifyContent:'center',alignItems:'center',marginBottom:'20px'}}>
                <div style={{display:'flex',width:'90%',border:'1px solid',padding:'20px',marginBottom:'10px'}}>
                  <div style={{width:'5%',display:'flex',alignItems:'center',justifyContent:'center'}}>Q{qno}:</div>
                  <div style={{width:'95%'}}><textarea id='textarea' style={{height:'100%',width:'100%'}} value={question} onChange={(e)=>setQuestion(e.target.value)} required/></div>
                </div>
                <div style={{border:'1px solid',width:'95%',display:'flex',flexDirection:'column'}}>
                    <div style={{display:'flex',justifyContent:'space-around',paddingBottom:'7px'}}>
                        <div key={"option1"}>A:<input type="text" name="options" id="options" value={options[0]} required autoComplete='on' onChange={(e)=>{var updated = [...options];updated[0] = e.target.value;setOptions(updated)}} /></div>
                        <div>B:<input type="text" name="option2" id="option2" value={options[1]} required onChange={(e)=>{var updated = [...options];updated[1] = e.target.value;setOptions(updated)}} /></div>
                    </div>
                    <div style={{display:'flex',justifyContent:'space-around',marginBottom:'20px'}}>
                        <div>C:<input type="text" name="option3" id="option3" value={options[2]} required onChange={(e)=>{var updated = [...options];updated[2] = e.target.value;setOptions(updated)}} /></div>
                        <div>D:<input type="text" name="option4" id="option4" value={options[3]} required onChange={(e)=>{var updated = [...options];updated[3] = e.target.value;setOptions(updated)}} /></div>
                    </div>
                    <div style={{display:'flex',justifyContent:'space-between'}}>
                      <div style={{width:'50%'}}>ANSWER:<input type='text' id='ans' name='ans' value={answer} onChange={(e)=>{setAnswer(e.target.value)}} /></div>
                      <div style={{width:'20%',display:'flex',justifyContent:'space-evenly'}}>
                          {
                            qno===20?(<div style={{display:'flex',justifyContent:'space-evenly',gap:'12px'}}>
                                      <button type="submit" id='savebutton' onClick={()=>{document.getElementById('finishbutton').disabled=false;
                                                                      document.getElementById('savebutton').style.display='none';}}>SAVE</button>
                                      <button type="submit" id='finishbutton' onClick={()=>{alert("FINISHED");setDisplayque(0);}} disabled>
                                        FINISH
                                      </button></div>):(<button type="submit">SAVE & NEXT</button>)
                          }
                      </div>
                    </div>
                </div>
              </div>
              <center><p style={{color:'blue'}}>*Click On Save&Next To Add More Questions</p></center>
              </form>
              );
            }
  else{
    alert("maximum questions reached.")
  }

  return (
    <div>
      <form onSubmit={handleaddquestions}>
        <table align="center">
          <tbody>
            <tr>
              <td>COURSE</td>
              <td>:</td>
              <td>
                <select name="course" id="course">
                  <option value="btech">BTECH</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>BATCH</td>
              <td>:</td>
              <td>
                <select
                  name="batch"
                  id="batch"
                  value={batch}
                  onChange={(e) => {
                    const selectedValue = e.target.value;
                    setBatch(selectedValue);
                    handleregulation(selectedValue);
                  }}
                >
                  <option value="2021">2021</option>
                  <option value="2022">2022</option>
                  <option value="2023">2023</option>
                  <option value="2024">2024</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>BRANCH</td>
              <td>:</td>
              <td>
                <select
                  name="branch"
                  id="branch"
                  value={branch}
                  onChange={(e) => setBranch(e.target.value)}
                >
                  <option value="CSE">COMPUTER SCIENCE AND ENGINEERING</option>
                  <option value="ECE">ELECTRONICS AND COMMUNICATION ENGINEERING</option>
                  <option value="EEE">ELECTRICAL AND ELECTRONICS ENGINEERING</option>
                  <option value="ME">MECHANICAL ENGINEERING</option>
                  <option value="CE">CIVIL ENGINEERING</option>
                  <option value="CST">COMPUTER SCIENCE AND TECHNOLOGY</option>
                  <option value="CAI">COMPUTER SCIENCE AND ENGINEERING (AI)</option>
                  <option value="AIM">COMPUTER SCIENCE AND ENGINEERING (AIM)</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>SEMESTER</td>
              <td>:</td>
              <td>
                <select
                  name="semester"
                  id="semester"
                  value={semester}
                  onChange={(e) => setSemester(e.target.value)}
                >
                  <option value="1">I</option>
                  <option value="2">II</option>
                  <option value="3">III</option>
                  <option value="4">IV</option>
                  <option value="5">V</option>
                  <option value="6">VI</option>
                  <option value="7">VII</option>
                  <option value="8">VIII</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>SUBJECT</td>
              <td>:</td>
              <td>
                <select name="subject" id="subject-select" value={ccode} onChange={(e)=>{setCcode(e.target.value);console.log(e.target.value)}} >
                  {Array.isArray(subjects.subjectname) && subjects.subjectname.length > 0 ? (
                    subjects.subjectname.map((name, index) => (
                      <option key={index} value={subjects.coursecode?.[index]}>
                        {name}
                      </option>
                    ))
                  ) : (
                    <option disabled>No subjects available</option>
                  )}
                </select>
              </td>
            </tr>

            <tr>
              <td>EXAM</td>
              <td>:</td>
              <td>
                <select name="exam" id="exam-select" value={exam_type} onChange={(e)=>{setExam_type(e.target.value)}}>
                  <option value="MID-1">MID-1</option>
                  <option value="MID-2">MID-2</option>
                </select>
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colSpan={3} align='center'><button type='submit'>Upload Questions</button></td>
            </tr>
          </tfoot>
        </table>
      </form>
      <div style={{marginTop:'10px',marginBottom:'10px'}}>
      {displayque ===1 ? (divs):(null)}
      </div>
    </div>
  );
}

export default Conductexam;
