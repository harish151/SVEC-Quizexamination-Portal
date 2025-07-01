import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useLocation } from 'react-router-dom';
import Login from './Login';
import Signup from './Signup'
import Conductexam from './Conductexam';
import ViewQuestions from './ViewQuestions';
import DashBoard from './DashBoard';
import Viewresult from './Viewresult';

function Employee() {
  const location = useLocation();
  const navigate = useNavigate();
  const name = location.state?.name || null;
  const username = location.state?.username || null;
  const [component,setComponent] = useState(<DashBoard />)

  if (!name || !username) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh' }}>
        <h2 style={{ color: 'red' }}>ERROR: YOUR NOT AN AUTHORIZED PERSON.</h2>
      </div>
    );
  }

  return (
    <div style={{display:'flex',flexDirection:'row',width:'100vw'}}>
      <div className='menu' style={{display:'flex',flexDirection:'column',alignItems:'center',width:'16%',height:'100vh',padding:'2px'}}>
         <div style={{paddingBottom:'50px'}}><b> Welcome, {name}</b></div>
         <div style={{ height:'100vh',display:'flex',flexDirection:'column'}}>
            <Link className='link' style={{padding:'10px',textDecoration:'none',fontWeight:'bold',color:'black'}} onClick={(e)=>{e.preventDefault();setComponent(<DashBoard />);}}>DASHBOARD</Link>
            <Link className='link' style={{padding:'10px',textDecoration:'none',fontWeight:'bold',color:'black'}} onClick={(e)=>{e.preventDefault();setComponent(<Conductexam username={username}/>);}}>CONDUCT EXAM</Link>
            <Link className='link' style={{padding:'10px',textDecoration:'none',fontWeight:'bold',color:'black'}} onClick={(e)=>{e.preventDefault();setComponent(<ViewQuestions username={username}/>);}}>VIEW QUESTION PAPER</Link>
            <Link className='link' style={{padding:'10px',textDecoration:'none',fontWeight:'bold',color:'black'}} onClick={(e)=>{e.preventDefault();setComponent(<Viewresult />);}}>VIEW RESULTS</Link>
            <Link className='link' style={{padding:'10px',textDecoration:'none',fontWeight:'bold',color:'black'}} onClick={(e)=>{e.preventDefault();navigate("/")}}>LOGOUT</Link>
          </div>
      </div>

      <div style={{border:'1px solid',width:'80%'}}>
        {component}
      </div>
    </div>
  )
}

export default Employee
