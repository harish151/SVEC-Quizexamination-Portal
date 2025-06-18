import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
function Login() {
    const navigate = useNavigate();
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const [result,setResult] = useState("");
    const handle_login = (e)=>{
        e.preventDefault();
        console.log(email);
        console.log(password);
        axios.get("http://localhost:8080/login",{params:{email:email,password:password}})
        .then(res =>{setResult(res.data);console.log(result);if(res.data==='Correct') navigate("/dashboard");})
        .catch(err =>{console.log(err)} );
    }

  return (
    <div className='form-div'>
        <center>
      <form onSubmit={handle_login} className='login-signup-form'>
        <h2 style={{fontWeight:'bold',color:result==='Correct'?'green':'red'}}>{result}</h2>
        <div>
            <div><center><h1 style={{color:'white'}}>LOGIN</h1></center></div>
            <div style={{padding:'10px'}}>
                <div style={{display:'flex',padding:'10px'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold',color:'white'}}>EMAIL</div>
                    <div style={{width:'60%'}}><input type="text" name="email" id="email" placeholder='Enter Email' value={email} onChange={(e)=>{setEmail(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
                <div style={{display:'flex',padding:'10px'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold',color:'white'}}>PASSWORD</div>
                    <div style={{width:'60%'}}><input type="password" name="password" id="password" placeholder='Enter Password' value={password} onChange={(e)=>{setPassword(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
            </div>
            <div style={{display:'flex',justifyContent:'center'}}>
                <button type='submit'>LOGIN</button>
            </div>
            <div style={{display:'flex',flexDirection:'column',justifyContent:'flex-start',paddingTop:'15px'}}>
                <div style={{padding:'10px',textAlign:'left'}}><Link to="/" style={{color:'white'}}>Forgot Password?</Link></div>
                <div style={{padding:'10px',textAlign:'left'}}><Link to="/signup" style={{color:'white'}}>Sign Up!</Link></div>
            </div>
        </div>
      </form>
      </center>
    </div>
  )
}

export default Login
