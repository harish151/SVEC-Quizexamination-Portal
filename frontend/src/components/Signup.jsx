import React,{ useState } from 'react';
import { Link,useNavigate } from 'react-router-dom';
import axios from 'axios';

function Signup() {
    const navigate = useNavigate();
    const [email,setEmail] = useState("");
    const [password,setPassword] = useState("");
    const [newpassword,setNewpassword] = useState("");
    const [result,setResult] = useState("");

    const handle_signup = (e)=>{
        e.preventDefault();
        console.log(email);
        console.log(password);
        console.log(newpassword);
        if(password===newpassword){
        axios.post(`http://${import.meta.env.VITE_HOST}:8080/signup`,{email:email,password:password})
        .then(res =>{setResult(res.data===1?(alert("!Account Created Click Ok To SignIn"),navigate('/')):(alert("User Already Exist")));})
        .catch(err =>{console.log(err)} );
        }
        else{
          alert("Both password and Confirm password will be same.")
        }

    }
  return (
    <div className='form-div'>
        <center>
      <form onSubmit={handle_signup} className='login-signup-form'>
        <h2 style={{fontWeight:'bold',color:result=='User Already Exist'?'red':'green'}}>{result}</h2>
        <div>
            <div><center><h1 style={{color:'white'}}>SIGN UP</h1></center></div>
            <div style={{padding:'10px'}}>
                <div style={{display:'flex',padding:'10px'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold',color:'white'}}>EMAIL</div>
                    <div style={{width:'60%'}}><input type="text" name="email" id="email" placeholder='Enter Email' value={email} onChange={(e)=>{setEmail(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
                <div style={{display:'flex',padding:'10px'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold',color:'white'}}>PASSWORD</div>
                    <div style={{width:'60%'}}><input type="password" name="password" id="password" placeholder='Enter Password' value={password} onChange={(e)=>{setPassword(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
                <div style={{display:'flex',padding:'10px'}}>
                    <div style={{width:'40%',textAlign:'center',paddingRight:'10px',fontWeight:'bold',color:'white'}}>Confirm PASSWORD</div>
                    <div style={{width:'60%'}}><input type="password" name="newpassword" id="newpassword" placeholder='Enter new Password' value={newpassword} onChange={(e)=>{setNewpassword(e.target.value)}} autoComplete='on' required style={{width:'100%'}}/></div>
                </div>
            </div>
            <div style={{display:'flex',justifyContent:'center'}}>
                <button type='submit'>SIGNUP</button>
            </div>
            <div style={{display:'flex',flexDirection:'column',justifyContent:'flex-start',paddingTop:'15px'}}>
                <div style={{padding:'10px',textAlign:'left',color:'white'}}><Link to="/" style={{color:'white'}}>Sign In!</Link></div>
            </div>
            
        </div>
      </form>
      </center>
    </div>
  )
}

export default Signup
