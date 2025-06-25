import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useLocation } from 'react-router-dom';
import Login from './Login';
import Signup from './Signup'
import Conductexam from './Conductexam';

function Employee() {
  const [component,setComponent] = useState(<Conductexam />)
  const location = useLocation();
  const navigate = useNavigate();
  const username = location.state?.name || "Guest";
  return (
    <div style={{display:'flex',flexDirection:'row',width:'100vw'}}>
      <div style={{display:'flex',flexDirection:'column',alignItems:'center',border:'1px solid',width:'16%',height:'100vh',padding:'2px'}}>
         <div style={{paddingBottom:'50px'}}> Welcome, {username}</div>
         <div style={{ height:'100vh',display:'flex',flexDirection:'column'}}>
            <Link style={{padding:'10px',textDecoration:'none'}} onClick={(e)=>{e.preventDefault();}}>DASHBOARD</Link>
            <Link style={{padding:'10px',textDecoration:'none'}} onClick={(e)=>{e.preventDefault();setComponent(<Conductexam/>);}}>CONDUCT EXAM</Link>
            <Link style={{padding:'10px',textDecoration:'none'}} onClick={(e)=>{e.preventDefault()}}>VIEW QUESTION PAPER</Link>
            <Link style={{padding:'10px',textDecoration:'none'}} onClick={(e)=>{e.preventDefault()}}>VIEW RESULTS</Link>
            <Link style={{padding:'10px',textDecoration:'none'}} onClick={(e)=>{e.preventDefault();navigate("/")}}>LOGOUT</Link>
          </div>
      </div>

      <div style={{border:'4px solid GREEN',width:'80%'}}>
        {component}
      </div>
    </div>
  )
}

export default Employee
