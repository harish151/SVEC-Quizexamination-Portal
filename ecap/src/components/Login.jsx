import React from 'react';

function Login() {

    
  return (
    <div className='login-div'>
      <div className='login-form'>
        <div className='person-details'>
            <form action="" className='person-form'>
                <table border={0} cellPadding={15} cellSpacing={15}  style={{borderCollapse:'collapse',borderRadius:'25px',height:'100%'}}>
                    <thead>
                        <tr>
                            <td colSpan={3} align='right' style={{fontWeight:'bold',backgroundColor:'aqua',borderTopLeftRadius:'25px',borderTopRightRadius:'25px'}}><h1>EMPLOYEE</h1></td>
                        </tr>
                    </thead>
                        <tbody>
                        <tr height={20}>
                            <td align='right'>USERNAME</td>
                            <td>:</td>
                            <td><input type='text'/></td>
                        </tr>
                        <tr height={20}>
                            <td align='right'>PASSWORD</td>
                            <td>:</td>
                            <td><input type='password' /></td>
                        </tr>
                        <tr height={20}>
                            <td colSpan={3} align='right'><button type='submit'>LOGIN</button></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <div className='person-details'>
            <form action="" className='person-form'>
                 <table border={0} cellPadding={15} cellSpacing={15} style={{borderCollapse:'collapse',borderRadius:'25px'}}>
                    <thead>
                        <tr>
                            <td colSpan={3} align='right' style={{fontWeight:'bold',backgroundColor:'aqua',borderTopLeftRadius:'25px',borderTopRightRadius:'25px'}} ><h1>STUDENT</h1></td>
                        </tr>
                    </thead>
                        <tbody>
                        <tr height={20}>
                            <td align='right'>USERNAME</td>
                            <td>:</td>
                            <td><input type='text'/></td>
                        </tr>
                        <tr height={20}>
                            <td align='right'>PASSWORD</td>
                            <td>:</td>
                            <td><input type='password' /></td>
                        </tr>
                        <tr height={20}>
                            <td colSpan={3} align='right'><button type='submit'>LOGIN</button></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
      </div>
    </div>
  )
}

export default Login
