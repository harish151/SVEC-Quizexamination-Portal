import React, { useState,useRef } from 'react'
import { useLocation,useNavigate } from 'react-router-dom';
import ExamSchedule from './ExamSchedule';
import ConductExam from './ConductExam';
import ViewQuestions from './ViewQuestions';
import ViewResults from './ViewResults';
function Employee() {
  const location = useLocation();
  const navigate = useNavigate();
  const details = location.state?.details || null;
  const token = location.state?.token || null;
  const [page,setPage] = useState(<ExamSchedule branch={details[0].branch} details={details} token={token} />);
  const dashboardRef = useRef(null);
  const conductexamRef = useRef(null);
  const viewqueRef = useRef(null);
  const viewresultRef = useRef(null);
  const logoutRef = useRef(null);

  if (!details) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <h2 style={{ color: 'red' }}>ERROR: YOUR NOT AN AUTHORIZED PERSON.</h2>
      </div>
    );
  }

  return (
    <div className='h-100 w-100'>
      <div className="p-2 bg-info text-dark w-100 text-start text-uppercase"><b>WELCOME {details?.name || "Student"}</b></div>
        <div className='d-inline-block w-100 d-flex flex-row border' style={{height:'100vh'}}>
          <div className='border vertical list-group' style={{width:'16%',height:'100vh',borderRadius:'0'}}>
            <button className="list-group-item list-group-item-action active" ref={dashboardRef} data-bs-toggle="list" role="tab" 
            onClick={()=>{ dashboardRef.current.classList.add("active");
                           conductexamRef.current.classList.remove("active");
                           viewresultRef.current.classList.remove("active");
                           viewqueRef.current.classList.remove("active");
                           logoutRef.current.classList.remove("active");
                           setPage(<ExamSchedule details={details} branch={details[0].branch} token={token} />)
            }}>DASHBOARD</button>
            <button className="list-group-item list-group-item-action" ref={conductexamRef} data-bs-toggle="list" role="tab" 
            onClick={()=>{conductexamRef.current.classList.add("active");
                          viewresultRef.current.classList.remove("active");
                          dashboardRef.current.classList.remove("active");
                          viewqueRef.current.classList.remove("active");
                          logoutRef.current.classList.remove("active");
                          setPage(<ConductExam username={details[0].username} />);
            }}>CONDUCT EXAM</button>
            <button className="list-group-item list-group-item-action" ref={viewqueRef} id='exams' data-bs-toggle="list" role="tab" 
            onClick={()=>{dashboardRef.current.classList.remove("active");
                          viewresultRef.current.classList.remove("active");
                          conductexamRef.current.classList.remove("active");
                          viewqueRef.current.classList.add("active");
                          logoutRef.current.classList.remove("active");
                          setPage(<ViewQuestions username={details[0].username} />)
            }} >VIEW QUESTION PAPER</button>
            <button className="list-group-item list-group-item-action" ref={viewresultRef} data-bs-toggle="list" role="tab"
             onClick={()=>{dashboardRef.current.classList.remove("active");
                          conductexamRef.current.classList.remove("active");
                          viewqueRef.current.classList.remove("active");
                          logoutRef.current.classList.remove("active");
                          viewresultRef.current.classList.add("active");
                          setPage(<ViewResults username={details[0].username} />)
            }} >VIEW RESULTS</button>
            <button className="list-group-item list-group-item-action" ref={logoutRef} data-bs-toggle="list" role="tab"
             onClick={()=>{dashboardRef.current.classList.remove("active");
                          viewresultRef.current.classList.remove("active");
                          conductexamRef.current.classList.remove("active");
                          viewqueRef.current.classList.remove("active");
                          logoutRef.current.classList.add("active");
                          navigate("/");
            }} >LOGOUT</button>
          </div>
          <div className='border h-100' style={{width:'84%'}}>
            {page}
          </div>
        </div>
    </div>
  )
}

export default Employee
