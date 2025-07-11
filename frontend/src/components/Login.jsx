import React from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
function Login() {
const navigate = useNavigate();
    const [username,setUsername] = useState("");
    const [password,setPassword] = useState("");
    const [result,setResult] = useState("");
    const handle_login = (e)=>{
        e.preventDefault();
         axios.get(`http://${import.meta.env.VITE_HOST}:8080/login`,{params:{username:username,password:password}})
         .then(res =>{
              if(res.data==null){
                setResult("Invalid Username And Password")
              }
              else if(res.data.role.toLowerCase()==="student"){
                console.log(res.data);
                navigate("/student",{state:{details:res.data}});
              }
              else if(res.data.role.toLowerCase()==="teacher"){
                console.log(res.data);
                navigate("/employee",{state:{details:res.data}});
              }
          })
         .catch(err =>{console.log(err)} );
    }

  return (
    <div style={{width:'100%',height:'100vh',display:'flex',justifyContent:'center',alignItems:'center',border:'1px solid'}}>
      <form onSubmit={handle_login}>
        <center><h4 style={{fontWeight:'bold',color:'red'}}>{result}</h4></center>
        <div className="row mb-3 mw-100 border row-gap-3" style={{width:'100%',display:'flex',flexDirection:'column',borderRadius:'20px',paddingTop:'0 0 0 0'}}>
            <p className="fs-2 fw-medium border-bottom" style={{padding:'15px 15px 15px 15px',backgroundColor:'skyblue',borderTopLeftRadius:'20px',borderTopRightRadius:'20px',textAlign:'center'}}>LOGIN</p>
            <div className="col-sm-10 gap-3" style={{width:'fit-content',display:'flex',justifyContent:'center',alignItems:'center'}}>
                <b>USERNAME</b><input type="text" name="Username" value={username} onChange={(e)=>{setUsername(e.target.value)}} autoComplete='on' style={{position:'relative'}} className="form-control h-25 d-inline-block" placeholder="Enter Username" required />
            </div>
            <div className="col-sm-10 gap-3" style={{width:'fit-content',display:'flex',justifyContent:'center',alignItems:'center'}}>
                <b>PASSWORD</b><input type="password" name="password" value={password} onChange={(e)=>{setPassword(e.target.value)}} autoComplete='on' className="form-control h-25 d-inline-block" placeholder="Enter Password" required />
            </div>
            <center><button type="submit" className="btn btn-primary btn-sm " style={{width:'fit-content',backgroundColor:'skyblue',marginBottom:'15px'}}>LOGIN</button></center>
        </div>

        </form>
    </div>
  )
}

export default Login
