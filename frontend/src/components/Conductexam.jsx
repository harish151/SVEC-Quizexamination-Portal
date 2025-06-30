import React, { useEffect, useState } from 'react';
import axios from 'axios';
import FormComponent from './Form';

function Conductexam({username}) {
  // console.log(username);

  const [regulation, setRegulation] = useState("V20");
  const [batch, setBatch] = useState("2021");
  const [branch, setBranch] = useState("CSE");
  const [semester, setSemester] = useState("1");
  const [subjects, setSubjects] = useState({});
  const [sections,setSections] = useState(["ALL"]);
  const [ccode,setCcode] = useState("");
  const [exam_type,setExam_type] = useState("MID-1");
  const [displayque,setDisplayque] = useState(0);
  const [question,setQuestion] = useState("");
  const [options, setOptions] = useState(["", "", "", ""]);
  const [answer,setAnswer]=useState("");
  const [qno,setQno] = useState(1);
  const [buttonname,setButtonname] = useState("Upload Questions");

  const handleregulation = (selectedBatch,selectedbranch) => {
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/getregulation`, {
      params: { batch: selectedBatch, branch:selectedbranch }
    })
    .then(res => {
      console.log(res.data[0].regulation);
      setRegulation(res.data[0].regulation);
    })
    .catch(err => alert(err));
  };

  const handleaddquestions =(e)=>{
    e.preventDefault();
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/checkeligibility`,{params:{username:username,coursecode:ccode}})
    .then(res =>{
      if(res.data==="eligible"){
          axios.get(`http://${import.meta.env.VITE_HOST}:8080/getnumofqueposted`,{params:{batch:batch,exam_type:exam_type,branch:branch,coursecode:ccode}})
          .then(res =>{
            if(res.data===20){
              alert("Already Question are Assigned. Click on view question paper to see.");
            }
            else{
              setDisplayque(1);
              setQno(res.data+1);
            }
          })
          .catch(err => alert(err))
        }
        else{
          setDisplayque(0);
          alert("you are not eligible to assign questions to this subject.");
        }
      
    })
    
  }

  const handlequestion =(e,i=0)=>{
    e.preventDefault();
    axios.post(`http://${import.meta.env.VITE_HOST}:8080/addquestions`,{batch:batch,exam_type:exam_type,branch:branch,semester:semester,coursecode:ccode,question_no:qno,question:question,options:options,answer:answer})
    .then(res => {console.log(res.data);
                  if(i===0){setQno(qno+1);}
                  else if(i===13){
                      alert("successfully posted all questions.");
                      setDisplayque(0);
                    }
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
                      <div style={{width:'50%'}}>ANSWER:<input type='text' id='ans' name='ans' value={answer} onChange={(e)=>{setAnswer(e.target.value)}} required /></div>
                      <div style={{width:'20%',display:'flex',justifyContent:'space-evenly'}}>
                          {
                            qno===20?(<div style={{display:'flex',justifyContent:'space-evenly',gap:'12px'}}>
                                      <button type="submit"submit id='savebutton' onClick={(e)=>{handlequestion(e,13);}}>SAVE</button>
                                      </div>):(<button type="submit">SAVE & NEXT</button>)
                          }
                      </div>
                    </div>
                </div>
              </div>
              <center><p style={{color:'blue'}}>*Click On Save&Next To Add More Questions</p></center>
              </form>
              );
            }

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
        setDisplay={setDisplayque}
        buttonname = {buttonname}
        setButtonname = {setButtonname}
        handleregulation={handleregulation}
        handlequestions={handleaddquestions}
      />
      <div style={{marginTop:'10px',marginBottom:'10px'}}>
      {displayque ===1 ? (divs):(null)}
      </div>
    </div>
  );
}

export default Conductexam;
