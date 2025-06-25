import axios from 'axios'
import React, { useEffect, useState } from 'react'

function DashBoard() {
    const [users,SetUsers] = useState([]);
    useEffect(()=>{
    axios.get(`http://${import.meta.env.VITE_HOST}:8080/dashboard`)
    .then(res=>SetUsers(res.data))
    .catch(err=>console.log(err))
},[])
  return (
    <div>
      <table border={1}>
        <thead>
            <tr>
                <td style={{color:'white'}}>Email</td>
                <td style={{color:'white'}}>Password</td>
            </tr>
        </thead>
        <tbody>
            {
            users.map((data,index)=>
                <tr key={index}>
                    <td style={{color:'white'}}>{data.email}</td>
                    <td style={{color:'white'}}>{data.password}</td>
                </tr>
            )
        }
        </tbody>
      </table>
    </div>
  )
}

export default DashBoard
