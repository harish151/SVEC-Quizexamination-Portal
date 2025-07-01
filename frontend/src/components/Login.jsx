import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
function Login() {
    const navigate = useNavigate();
    const [username,setUsername] = useState("");
    const [password,setPassword] = useState("");
    const [result,setResult] = useState("");
    const handle_login = (e)=>{
        e.preventDefault();
         axios.get(`http://${import.meta.env.VITE_HOST}:8080/login`,{params:{username:username,password:password}})
         .then(res =>{
              console.log(result);
              if(res.data==null){
                setResult("Invalid Username And Password")
              }
              else if(res.data.role.toLowerCase()==="student"){
                 navigate("/student",{state:{details:res.data}});
              }
              else if(res.data.role.toLowerCase()==="teacher"){
                navigate("/employee",{state:{name:res.data.name,username:res.data.username,teachsub:res.data.teachsub}});
              }
          })
         .catch(err =>{console.log(err)} );
    }

  return (
    <div className='login'>
    {/* <div><marquee behavior="" direction=""><b>QUIZ WEB APP. PLEASE LOGIN TO EXPLORE MORE.</b></marquee></div> */}
    <div className='form-div'>
        <center>
      <form onSubmit={handle_login} className='login-signup-form'>
        <h4 style={{fontWeight:'bold',color:'red'}}>{result}</h4>
        <div>
            <div><center><h1>LOGIN</h1></center></div>
            <div style={{padding:'10px'}}>
                <div style={{display:'flex',padding:'10px'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold'}}>USERNAME</div>
                    <div style={{width:'60%'}}><input type="text" name="Username" id="Username" placeholder='Enter Username' value={username} onChange={(e)=>{setUsername(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
                <div style={{display:'flex',padding:'10px',paddingTop:'20PX'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold'}}>PASSWORD</div>
                    <div style={{width:'60%'}}><input type="password" name="password" id="password" placeholder='Enter Password' value={password} onChange={(e)=>{setPassword(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
            </div>
            <div style={{display:'flex',justifyContent:'center',paddingTop:'20px'}}>
                <button type='submit' className='button' >LOGIN</button>
            </div>
        </div>
      </form>
      </center>
    </div>
    </div>
  )
}

export default Login
