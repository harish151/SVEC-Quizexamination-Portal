import React from 'react'
import { Link } from 'react-router-dom'

function Employee() {
  return (
    <div>
      <div style={{display:'flex',flexDirection:'column',justifyContent:'center',alignItems:'center',border:'1px solid',width:'20%',height:'100vh'}}>
          <Link>DASHBOARD</Link>
          <Link>QUESTION BANK</Link>
          <Link>CONDUCT EXAM</Link>
          <Link>VIEW RESULTS</Link>
      </div>
      <div></div>
    </div>
  )
}

export default Employee
