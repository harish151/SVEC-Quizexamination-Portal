import React from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
function Login() {
const navigate = useNavigate();
    const [empusername,setEmpUsername] = useState("");
    const [emppassword,setEmppassword] = useState("");
    const [stuusername,setStuUsername] = useState("");
    const [stupassword,setStupassword] = useState("");
    const [result,setResult] = useState("");
    const emp_handle_login = (e)=>{
        e.preventDefault();
         axios.get(`http://${import.meta.env.VITE_HOST}:8080/noauth/loginemp`,{params:{username:empusername,password:emppassword}})
         .then(res =>{console.log(res.data.details[0].name+" "+res.data.token);
              if(res.data.details[0].role.toLowerCase()==="teacher"){
                console.log(res.data);
                navigate("/employee",{state:{details:res.data.details,token:res.data.token}});
              }
              else{
                setResult("Invalid Username And Password")
              }
          })
         .catch(err =>{console.log(err)} );
    }

    const stu_handle_login = (e)=>{
        e.preventDefault();
         axios.get(`http://${import.meta.env.VITE_HOST}:8080/noauth/loginstu`,{params:{username:stuusername,password:stupassword}})
         .then(res =>{
              if(res.data.details[0].role.toLowerCase()==="student"){
                navigate("/student",{state:{details:res.data.details,token:res.data.token}});
              }
              else{
                setResult("Invalid Username And Password")
              }
          })
         .catch(err =>{console.log(err)} );
    }

  return (
    <div style={{width:'100%',height:'100vh',display:'flex',justifyContent:'center',alignItems:'center',gap:'10%',border:'1px solid'}}>
      <form onSubmit={emp_handle_login}>
        <center><h4 style={{fontWeight:'bold',color:'red'}}>{result}</h4></center>
        <div className="row mb-3 mw-100 border row-gap-3" style={{width:'100%',display:'flex',flexDirection:'column',borderRadius:'20px',paddingTop:'0 0 0 0'}}>
            <p className="fs-2 fw-medium border-bottom" style={{padding:'15px 15px 15px 15px',backgroundColor:'skyblue',borderTopLeftRadius:'20px',borderTopRightRadius:'20px',textAlign:'center'}}>EMPLOYEE</p>
            <div className="col-sm-10 gap-3" style={{width:'fit-content',display:'flex',justifyContent:'center',alignItems:'center'}}>
                <b>USERNAME</b><input type="text" name="Username" value={empusername} onChange={(e)=>{setEmpUsername(e.target.value)}} autoComplete='on' style={{position:'relative'}} className="form-control h-25 d-inline-block" placeholder="Enter Username" required />
            </div>
            <div className="col-sm-10 gap-3" style={{width:'fit-content',display:'flex',justifyContent:'center',alignItems:'center'}}>
                <b>PASSWORD</b><input type="password" name="password" value={emppassword} onChange={(e)=>{setEmppassword(e.target.value)}} autoComplete='on' className="form-control h-25 d-inline-block" placeholder="Enter Password" required />
            </div>
            <center><button type="submit" className="btn btn-primary btn-sm " style={{width:'fit-content',backgroundColor:'skyblue',marginBottom:'15px'}}>LOGIN</button></center>
        </div>

        </form>

      <form onSubmit={stu_handle_login}>
        <center><h4 style={{fontWeight:'bold',color:'red'}}>{result}</h4></center>
        <div className="row mb-3 mw-100 border row-gap-3" style={{width:'100%',display:'flex',flexDirection:'column',borderRadius:'20px',paddingTop:'0 0 0 0'}}>
            <p className="fs-2 fw-medium border-bottom" style={{padding:'15px 15px 15px 15px',backgroundColor:'skyblue',borderTopLeftRadius:'20px',borderTopRightRadius:'20px',textAlign:'center'}}>STUDENT</p>
            <div className="col-sm-10 gap-3" style={{width:'fit-content',display:'flex',justifyContent:'center',alignItems:'center'}}>
                <b>USERNAME</b><input type="text" name="Username" value={stuusername} onChange={(e)=>{setStuUsername(e.target.value)}} autoComplete='on' style={{position:'relative'}} className="form-control h-25 d-inline-block" placeholder="Enter Username" required />
            </div>
            <div className="col-sm-10 gap-3" style={{width:'fit-content',display:'flex',justifyContent:'center',alignItems:'center'}}>
                <b>PASSWORD</b><input type="password" name="password" value={stupassword} onChange={(e)=>{setStupassword(e.target.value)}} autoComplete='on' className="form-control h-25 d-inline-block" placeholder="Enter Password" required />
            </div>
            <center><button type="submit" className="btn btn-primary btn-sm " style={{width:'fit-content',backgroundColor:'skyblue',marginBottom:'15px'}}>LOGIN</button></center>
        </div>

        </form>
    </div>
  )
}

export default Login
