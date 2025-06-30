import axios from 'axios';
import { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';

function Exam() {
    const location = useLocation();
    const batch = location.state?.batch || null;
    const branch = location.state?.branch || null;
    const coursecode = location.state?.coursecode || null;
    const exam_type = location.state?.examtype || null;
    const semester = location.state?.semester || null;
    const section = location.state?.section || null;
    const username = location.state?.username || null;

    const [questions,setQuestions] = useState([]);
    const [qno,setQno]=useState(0);
    const [answers, setAnswers] = useState(new Array(20).fill(null));
    const [timeLeft, setTimeLeft] = useState(6*60); 
    var divs =[]
    for(let i=0;i<20;i++){
            divs.push(<div key={i+1} style={{display:'flex',margin:'0px',justifyContent:'space-around',border:'1px solid',height:'fit-content',width:'10%'}}>
                {i+1}
            </div>)
    }

    const handleans =(e)=>{
      e.preventDefault();
      axios.post(`http://${import.meta.env.VITE_HOST}:8080/setresults`,{ans:answers,batch:batch,branch:branch,coursecode:coursecode,examType:exam_type,semester:semester,section:section,username:username})
        .then(res => {console.log(res.data)})
        .catch(err => alert(err));
    }

    useEffect(()=>{
        axios.get(`http://${import.meta.env.VITE_HOST}:8080/examquestions`,{params:{batch:batch,branch:branch,coursecode:coursecode,exam_type:exam_type}})
        .then(res => {console.log(res.data);setQuestions(res.data)})
        .catch(err => alert(err));
    },[batch, branch, coursecode, exam_type])

    useEffect(() => {
      const timer = setInterval(() => {
        setTimeLeft(prev => {
          if (prev <= 1) {
            clearInterval(timer);
            alert("Time's up!");
            handleans({ preventDefault: () => {} });
            return 0;
          }
          return prev - 1;
        });
      }, 1000);
      return () => clearInterval(timer);
      }, []);

  return (
    <div style={{display:'flex'}}>
      <div style={{flexDirection:'row',display:'flex',flexWrap:'wrap',justifyContent:'space-around',width:'20vw',height:'30vh',columnGap:'30px',marginTop:'50px'}}>
            {
                divs.map((i)=>(i))
            }
      </div>
      <div style={{border:'1px solid',width:'80vw',height:'100vh'}}>
        <div style={{height:'5vw',display:'flex',justifyContent:'flex-end',alignItems:'center',fontSize:'30px'}} id='timer' className='timer' >
           <b style={{marginRight:'20px',color:(timeLeft>600)?'green':(timeLeft>180)?'orange':'red'}}>{`${Math.floor(timeLeft / 60).toString().padStart(2, '0')}:${(timeLeft % 60).toString().padStart(2, '0')}`}</b>
        </div>
        <form onSubmit={handleans} style={{border:'1px solid '}}>
            {questions[qno] && (<div><b>Q{qno+1}.{questions[qno].question}</b>
                {questions[qno].options.map((opt,index)=>(<div key={index} style={{padding:'5px'}}><input type='radio' id={`qno${qno}option${index}`} value={opt} checked={answers[qno] === opt} onChange={()=>{const updatedAnswers = [...answers];updatedAnswers[qno] = opt;setAnswers(updatedAnswers);console.log(answers)}}
                />
                <label htmlFor={`qno${qno}option${index}`} style={{ cursor: 'pointer' }}>{opt}</label>
                </div>))}
                </div>)}
            <button onClick={(e)=>{e.preventDefault();if(qno>0){setQno(qno-1)}}} >PREVIOUS</button>
            <button onClick={(e)=>{e.preventDefault();console.log(answers);if(qno<21){setQno(qno+1);if(qno===19){setQno(0)}}}} >SAVE & NEXT</button>
            <div style={{height:'75vh',width:'78vw',display:'flex',justifyContent:'flex-end',alignItems:'flex-end'}}><button type='submit'>SUBMIT</button></div>
            </form>
      </div>
      <div>
      </div>
    </div>
  )
}

export default Exam
