import React from 'react'
import { useState,useEffect,useRef } from 'react';
import axios from 'axios';
import { useNavigate,useLocation, Link } from 'react-router-dom';



function Exam() {
  const navigate = useNavigate();
  const location = useLocation();
  const name = location.state?.name || null;
  const batch = location.state?.batch || null;
  const branch = location.state?.branch || null;
  const coursecode = location.state?.coursecode || null;
  const examtype = location.state?.examtype || null;
  const semester = location.state?.semester || null;
  const section = location.state?.section || null;
  const username = location.state?.username || null;
  const image = location.state?.image || null;
  const role = location.state?.role || null;
  let sess = location.state?.session || false;
  const token = location.state?.token || false;
  const[session,setSession]=useState(sess);
  const [questions, setQuestions] = useState([]);
  const [qno, setQno] = useState(0);
  const [originalans,setOriginalans] = useState(new Array(20).fill(null));
  const [answers, setAnswers] = useState(new Array(20).fill(null));
  const [timeLeft, setTimeLeft] = useState(20*60);
  const [isActive, setIsActive] = useState(true);
  const [status, setStatus] = useState("incomplete");
  const intervalRef = useRef(null);
  const details =[{"batch":batch,"branch":branch,"name":name,"semester":semester,"section":section,"username":username,"role":role,"image":image}]
  const [exitcount,setExitcount]=useState(0);
  const [fullscreen,setFullscreen]=useState(true);
  const [getAnswers,setGetAnswers]=useState(new Array(20).fill(null))
  const [isreloaded,setIsreloaded] = useState(false);
  const submitRef = useRef(false);
  const isSubmitted = useRef(false);
  const examStateKey = `examState_${username}_${examtype}_${coursecode}`;

  useEffect(() => {
  const handleUnload = () => {
    console.log("Page is closing, cleanup here.");
    // cancel API calls, stop timers, etc.
  };

  window.addEventListener("beforeunload", handleUnload);
  return () => window.removeEventListener("beforeunload", handleUnload);
}, []);

  useEffect(() => {
    // Check sessionStorage for the reloaded state
    const reloaded = sessionStorage.getItem('reloaded');
    if (reloaded === 'true') {
      setIsreloaded(true);
      setFullscreen(false);
      togglePause();
      setIsActive(false);
    }
    else{
      window.addEventListener("beforeunload", (ev) => {
              ev.returnValue = "Exam cannot be submitted due to system sleep or power off.";
              alert("Exam cannot be submitted due to system sleep or power off.");
          });
      }
  }, []);


  useEffect(() => {
  const onFullscreenChange = () => {
    if (!document.fullscreenElement) {
      setFullscreen(false);
      if(exitcount<=0 && submitRef.current){
      togglePause();
      setIsActive(false);
      alert("dont click esc again.");
    }
    setExitcount((prev) => prev + 1);
    const el = document.getElementById("fullscreenbutton");
    if (el) {
        el.style.display = "flex";
    }
    }
  };

  document.addEventListener("fullscreenchange", onFullscreenChange);
  return () => document.removeEventListener("fullscreenchange", onFullscreenChange);
}, [exitcount]);

useEffect(() => {
  const handleVisibilityChange = (e) => {
    if (document.visibilityState === 'hidden' && document.hasFocus()) {
      calculatemarks(e, "auto-submitted");
    }
  };
  document.addEventListener('visibilitychange', handleVisibilityChange);
    return () => {
      document.removeEventListener('visibilitychange', handleVisibilityChange);
  };
}, [questions, originalans, answers]);
  

  const goFullscreen = () => {
        const elem = document.documentElement;
        const el = document.getElementById("fullscreenbutton");
        if (elem.requestFullscreen) {
          if (el) {
            el.style.display = "none";
          }
          elem.requestFullscreen().catch((err) =>
            console.error('Fullscreen error:', err)
        );
        } else if (elem.webkitRequestFullscreen) {
          if (el) {
            el.style.display = "none";
          }
          elem.webkitRequestFullscreen();
        } else if (elem.msRequestFullscreen) {
          if (el) {
            el.style.display = "none";
          }
          elem.msRequestFullscreen();
        }
    };

  const exitFullscreen = ()=>{
    submitRef.current = true;
    if (document.exitFullscreen) {
        document.exitFullscreen();
      } else if (document.webkitExitFullscreen) {
        document.webkitExitFullscreen();
      } else if (document.mozCancelFullScreen) {
        document.mozCancelFullScreen();
      } else if (document.msExitFullscreen) {
        document.msExitFullscreen();
      }
  }


 const updateprogress = (e,id,username,batch,exam_type,branch,semester,coursecode,question_no,question,options,answer,selectedopt)=>{
  e.preventDefault();
  axios.put(`http://${import.meta.env.VITE_HOST}:8080/student/updateprogress`, 
      {id:id,username:username,batch:batch,exam_type:exam_type,branch:branch,semester:semester,coursecode:coursecode,question_no:question_no,question:question,options:options,answer:answer,selectedopt:selectedopt},
      {
      headers: { Authorization: token, 'Content-Type': 'application/json'},
      withCredentials: true
    })
    .catch(err => alert(err))
 }

 const calculatemarks = (e, submissionStatus)=>{
    if (isSubmitted.current) {
        if (e && e.preventDefault) e.preventDefault();
        return;
    }
    isSubmitted.current = true;

    if (e && e.preventDefault) e.preventDefault();
    
    console.log(getAnswers);
    
    const finalStatus = submissionStatus || status;

    axios.post(`http://${import.meta.env.VITE_HOST}:8080/common/uploadresults`, 
      {batch:batch,branch:branch,semester:semester,coursecode:coursecode,examType: examtype,section:section,username:username,originalans:originalans,attemptedans:getAnswers,status:finalStatus},
      {
      headers: { Authorization: token, 'Content-Type': 'application/json'},
      withCredentials: true
    })
    .catch(err => alert(err))
    setSession(false);
    alert("exam submitted.");
    let t = true;
    if(t){
    navigate("/student",{state:{details,token}});}
  }

  const saveExamState = () => {
    const examState = {
      answers: answers,
      timeLeft: timeLeft,
      qno: qno,
    };
    localStorage.setItem(examStateKey, JSON.stringify(examState));
  };

  const restoreExamState = () => {
    const savedState = localStorage.getItem(examStateKey);
    if (savedState) {
      const { answers: savedAnswers, timeLeft: savedTimeLeft, qno: savedQno } = JSON.parse(savedState);
      setAnswers(savedAnswers);
      setTimeLeft(savedTimeLeft);
      setQno(savedQno);
    }
  };

  useEffect(() => {
    if(session){
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/student/examquestions`, {
        headers:{Authorization:token},
        withCredentials: true,
        params: { username:username ,batch:batch, branch:branch, coursecode:coursecode, examtype:examtype }
      })
      .then((res) => {
        setQuestions(res.data);
        restoreExamState();
        const ans = (res.data).map(q => q.answer);
        setOriginalans(ans);
        const selectedoption = (res.data).map(q => q.selectedopt);
        setAnswers(selectedoption);
        setGetAnswers(selectedoption);
        setIsActive(true);
        window.addEventListener("beforeunload", (ev) => {
              ev.preventDefault();
              sessionStorage.setItem('reloaded', 'false');
              setIsreloaded(true);
          });
      })
      .catch((err) => alert(err));}
  }, [batch, branch, coursecode, examtype]);

  useEffect(() => {
  if (isActive) {
    intervalRef.current = setInterval(() => {
      setTimeLeft((prev) => {
        if (prev > 0) {
          saveExamState();
          return prev - 1;
        } else {
          clearInterval(intervalRef.current);
          calculatemarks(null, "auto-submitted"); // auto-submit
          return 0; // ✅ must return a number
        }
      });
    }, 1000);
  } else {
    clearInterval(intervalRef.current);
  }

  return () => clearInterval(intervalRef.current);
}, [isActive]);


  const togglePause = () => {
    setIsActive((prev) => !prev);
  };

  const formatTime = (seconds) => {
    const m = Math.floor(seconds / 60).toString().padStart(2, '0');
    const s = (seconds % 60).toString().padStart(2, '0');
    
    return `${m}:${s}`;
  };

  // useEffect(() => {
  //   if (timeLeft > 0 && timeLeft % 10 === 0) {
  //     axios.post("http://localhost:8080/api/exam/save-time", {
  //       username: username,
  //       exam_type: examtype,
  //       remainingTime: timeLeft,
  //     })
  //     .then(() => console.log("Saved remaining time:", timeLeft))
  //     .catch(err => console.error("Error saving time:", err));
  //   }
  // }, [timeLeft]);

  const divs = [];
  for(let i=0;i<20;i++){
  divs.push(
    
      <li key={i} className='border' id={`qno${i}`} style={{width:'40px',height:'45px',lineHeight:'40px',textAlign:'center',cursor:'pointer',backgroundColor:getAnswers[i]!=null?'green':''}} onClick={()=>setQno(i)} >{i+1}</li>
  );}


  if (session==false) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <h2 style={{ color: 'red' }}>ERROR: GO BACK TO LOGIN.</h2>
      </div>
    );
  }


  return (
    <div >
       {console.log(isreloaded)}
       {
       isreloaded?(
       <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <h2 style={{ color: 'red' }}>ERROR: PAGE RELOADED.</h2>
      </div>
       ):
       (
        <>
        {


       fullscreen==false? (
          exitcount<=1 ?(<div id='fullscreenbutton' style={{display:'flex',backdropFilter:'blur(5px)',position:'absolute',width:'100%',height:'100%',alignItems:'center',justifyContent:'center'}}><button type='button' 
          onClick={(e)=>{e.preventDefault();goFullscreen();togglePause(); setIsActive(true);
                      }}>CONTINUE TO EXAM</button></div>):(calculatemarks())):("")}
                    
      <form onSubmit={calculatemarks}>
        <div className="d-flex justify-content-end" style={{alignItems:'center',marginRight:'2%'}}>
          <table border="0" cellPadding={10} >
            <tbody>
              <tr>
                <td><h4 className='fs-1'>{questions.length > 0 ? formatTime(timeLeft) : "Loading..."}</h4></td>
                <td><button type="submit" className="btn btn-success" ref={submitRef} onClick={()=>{exitFullscreen();setStatus("submitted")}}>SUBMIT</button></td>
              </tr>
            </tbody>
          </table>
        </div>
        <div className='d-flex border' style={{height:'100vh',width:'100vw'}}>
            <div className='border w-75' >
              { 
                questions[qno] && (<div style={{display:'flex',flexDirection:'column',padding:'10px'}}>
              <b style={{paddingBottom:'10px'}}>
                Q{qno + 1}.{questions[qno].question}
              </b>
              {questions[qno].options.map((opt, index) => (
                <div key={index} style={{ padding: '10px' }}>
                  <input type='radio' id={`qno${qno}option${index}`} name={`qno${qno}`} value={opt} checked={answers[qno] === opt}
                    onChange={() => {
                       const updatedAnswers = [...answers];
                       updatedAnswers[qno] = opt;
                       setAnswers(updatedAnswers);
                    }} />
                  <label htmlFor={`qno${qno}option${index}`} style={{ cursor: 'pointer' }}>
                    {opt}
                  </label>
                </div>
                ))}
                <div className='d-flex gap-5 justify-content-end'>
                  <button type="button" className="btn btn-outline-secondary" onClick={()=>{if(qno!=0){setQno(qno-1)}}} >Previous</button>
                  <button type="button" className="btn btn-outline-secondary" 
                      onClick={(e)=>{const updatedAnswers = [...answers];
                                    const selectedOption = document.querySelector(
                                      `input[name="qno${qno}"]:checked`
                                    )?.value;
                                    updatedAnswers[qno] = selectedOption;
                                    setGetAnswers(updatedAnswers);
                                    if(qno<19){setQno(qno+1)}else{setQno(0);}
                                    if(answers[qno]===null){document.getElementById(`qno${qno}`).style.backgroundColor = "red";}
                                    else{document.getElementById(`qno${qno}`).style.backgroundColor = "green";}
                                    updateprogress(e,questions[qno].id,username,batch,examtype,branch,semester,coursecode,qno+1,questions[qno].question,questions[qno].options,questions[qno].answer,answers[qno]);
                                    }}> &nbsp;&nbsp; Save & Next &nbsp;&nbsp;&nbsp; </button>
                </div>
                </div>
              )}
            </div>
            <div className='border w-25 d-flex' style={{flexWrap:'wrap',height:'0px',marginRight:'10px'}}>
              <ul className='d-flex flex-wrap align-content-start gap-4 mt-4' style={{listStyleType:'none'}}>
                  {divs}
              </ul>
              <br />
              <ul className='d-flex flex-wrap gap-4 w-100 mt-2' style={{listStyleType:'square'}}>
                <li style={{color:'green',fontSize:'20px'}}><p style={{color:'black',fontSize:'20px'}}>ANSWERED</p></li> &nbsp;
                <li style={{color:'red',fontSize:'20px'}}><p style={{color:'black',fontSize:'20px'}}>NOT ANSWERED</p></li>
              </ul>
            </div>
        </div>
      </form>
      </>
       )}
    </div>
  )
}

export default Exam;
