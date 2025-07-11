import React from 'react'
import { useLocation } from 'react-router-dom';

function Profile() {
  const location = useLocation();
  let details = location.state?.details || null;
  return (
    <div>
      <table className='table-bordered w-75 mt-4 ms-5' cellPadding={'5px'}>
        <thead>
          <tr style={{height:'5px'}}>
            <td colSpan={6} style={{backgroundColor:'grey',fontWeight:'bold'}}>STUDENT PROFILE</td>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td className='text-uppercase text-end'>BATCH</td>
            <td className='text-center' style={{width:'2px'}}>:</td>
            <td className='text-uppercase'>{details.batch}</td>
            <td rowSpan={3} colSpan={3}><center><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSuL6TBF6f4OmR3C6yj7pffvMkM13n9j6Prpg&s" alt={details.username} style={{width:'90px',height:'110px'}} /></center></td>
          </tr>
          <tr>
            <td className='text-uppercase text-end'>ROLL NO</td>
            <td className='text-center'>:</td>
            <td className='text-uppercase'>{details.username}</td>
          </tr>
          <tr>
            <td className='text-uppercase text-end'>NAME</td>
            <td className='text-center'>:</td>
            <td className='text-uppercase'>{details.name}</td>
          </tr>
          <tr>
            <td className='text-uppercase text-end'>BRANCH</td>
            <td className='text-center'>:</td>
            <td className='text-uppercase'>{details.branch}</td>
            <td className='text-uppercase text-end'>SEMESTER</td>
            <td className='text-center' style={{width:'2px'}}>:</td>
            <td className='text-uppercase'>{details.semester}</td>
          </tr>
          <tr>
            <td className='text-uppercase text-end'>SECTION</td>
            <td className='text-center'>:</td>
            <td className='text-uppercase'>{details.section}</td>
            <td className='text-uppercase text-end'>ROLE</td>
            <td className='text-center'>:</td>
            <td className='text-uppercase'>{details.role}</td>
          </tr>
        </tbody>
      </table>
    </div>
  )
}

export default Profile
