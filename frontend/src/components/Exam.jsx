import axios from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

function Exam() {
  const navigate = useNavigate();
  const location = useLocation();
  const name = location.state?.name || null;
  const batch = location.state?.batch || null;
  const branch = location.state?.branch || null;
  const coursecode = location.state?.coursecode || null;
  const exam_type = location.state?.examtype || null;
  const semester = location.state?.semester || null;
  const section = location.state?.section || null;
  const username = location.state?.username || null;

  const [questions, setQuestions] = useState([]);
  const [qno, setQno] = useState(0);
  const [answers, setAnswers] = useState(new Array(20).fill(null));
  const answersRef = useRef(answers); // 👈 Always holds the latest answers
  const [timeLeft, setTimeLeft] = useState((20) * 60);
const isSubmittedRef = useRef(false);  // replaces useState


  // Keep ref updated with every answer change
  useEffect(() => {
    answersRef.current = answers;
  }, [answers]);

  const handleans = async (e) => {
  if (e?.preventDefault) e.preventDefault();

  if (isSubmittedRef.current) return;
  isSubmittedRef.current = true;

  try {
    const res = await axios.post(`http://${import.meta.env.VITE_HOST}:8080/setresults`, {
      ans: answersRef.current,
      batch,
      branch,
      coursecode,
      examType: exam_type,
      semester,
      section,
      username,
    });
    console.log("Submitted:", res.data);
  } catch (err) {
    alert("Submission failed: " + err);
  }
};


  const handleTimeout = async () => {
    await handleans();
    navigate("/student", {
      state: {
        details: {
          name,
          batch,
          branch,
          coursecode,
          examtype: exam_type,
          semester,
          section,
          username,
        },
      },
    });
  };

  useEffect(() => {
    axios
      .get(`http://${import.meta.env.VITE_HOST}:8080/examquestions`, {
        params: { batch, branch, coursecode, exam_type },
      })
      .then((res) => {
        console.log(res.data);
        setQuestions(res.data);
      })
      .catch((err) => alert(err));
  }, [batch, branch, coursecode, exam_type]);

  useEffect(() => {
  const timer = setInterval(() => {
    setTimeLeft(prev => {
      if (prev <= 1) {
        clearInterval(timer);
        if (!isSubmittedRef.current) {
          alert("Time's up!");
          handleTimeout(); // will use handleans inside
        }
        return 0;
      }
      return prev - 1;
    });
  }, 1000);

  return () => clearInterval(timer);
}, []);


  const divs = [];
  for (let i = 0; i < 20; i++) {
    divs.push(
      <div
        key={i + 1}
        style={{
          display: 'flex',
          margin: '0px',
          justifyContent: 'space-around',
          border: '1px solid',
          height: 'fit-content',
          width: '10%',
        }}
      >
        {i + 1}
      </div>
    );
  }

  return (
    <div style={{ display: 'flex' }}>
      <div
        style={{
          flexDirection: 'row',
          display: 'flex',
          flexWrap: 'wrap',
          justifyContent: 'space-around',
          width: '20vw',
          height: '30vh',
          columnGap: '30px',
          marginTop: '50px',
        }}
      >
        {divs}
      </div>
      <div style={{ border: '1px solid', width: '80vw', height: '99.8vh' }}>
         <form onSubmit={handleans} style={{marginLeft:'20px'}}>
        <div
          style={{
            height: '5vw',
            display: 'flex',
            justifyContent: 'flex-end',
            alignItems: 'center',
            fontSize: '30px',
          }}
          className='timer'
        >
          <b
            style={{
              marginRight: '20px',
              color: timeLeft > 600 ? 'green' : timeLeft > 180 ? 'orange' : 'red',
            }}
          >
            {`${Math.floor(timeLeft / 60)
              .toString()
              .padStart(2, '0')}:${(timeLeft % 60).toString().padStart(2, '0')}`}
          </b>
          <button type="submit" disabled={isSubmittedRef.current} className='button' style={{padding:'3px 9px',marginRight:'20px'}}>SUBMIT</button>
        </div>
          {questions[qno] && (
            <div style={{display:'flex',flexDirection:'column',padding:'10px'}}>
              <b style={{paddingBottom:'10px'}}>
                Q{qno + 1}.{questions[qno].question}
              </b>
              {questions[qno].options.map((opt, index) => (
                <div key={index} style={{ padding: '10px' }}>
                  <input
                    type='radio'
                    id={`qno${qno}option${index}`}
                    value={opt}
                    checked={answers[qno] === opt}
                    onChange={() => {
                      const updatedAnswers = [...answers];
                      updatedAnswers[qno] = opt;
                      setAnswers(updatedAnswers);
                    }}
                  />
                  <label htmlFor={`qno${qno}option${index}`} style={{ cursor: 'pointer' }}>
                    {opt}
                  </label>
                </div>
              ))}
            </div>
          )}
          <button style={{marginLeft:'50px',marginRight:'20px'}} className='button'
            onClick={(e) => {
              e.preventDefault();
              if (qno > 0) setQno(qno - 1);
            }}
          >
            PREVIOUS
          </button>
          <button className='button'
            onClick={(e) => {
              e.preventDefault();
              if (qno < 19) setQno(qno + 1);
              else setQno(0);
            }}
          >
            SAVE & NEXT
          </button>
          {/* <div
            style={{
              
              display: 'flex',
              justifyContent: 'flex-end',
              alignItems: 'flex-end',
            }}
          >
            <button type="submit" disabled={isSubmittedRef.current}>SUBMIT</button>
          </div> */}
        </form>
      </div>
    </div>
  );
}

export default Exam;
