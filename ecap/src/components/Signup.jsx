import React, { useState } from 'react';

function Signup() {


  

  const btech = [
    "COMPUTER SCIENCE AND ENGINEERING",
    "COMPUTER SCIENCE AND TECHNOLOGY",
    "ELETRONICS AND COMMUNICATION ENGINEERING",
    "MECHANICAL ENGINEERING",
    "CIVIL ENGINEERING",
    "ELECTRICAL AND ELECTRONICS ENGINEERING",
    "COMPUTER SCIENCE AND ENGINEERING (AI)",
    "COMPUTER SCIENCE AND ENGINEERING (AIM)",
    "COMPUTER SCIENCE AND ENGINEERING (DATA SCIENCE)"
  ];

  const diploma = ["DCME", "DEEE"];

  const [branches, SetBranches] = useState(btech);
  const [course, SetCourse] = useState("1");

  const handlebranch = (e) => {
    const selectedCourse = e.target.value;
    SetCourse(selectedCourse);
    if (selectedCourse === "2") {
      SetBranches(diploma);
    } else {
      SetBranches(btech);
    }
  };

  return (
    <div>
      <center>
        <div style={{ marginTop: '1cm', border: '1px solid green', width: '80%', display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }}>
          <h1><u>STUDENT REGISTRATION FORM</u></h1>
          <form action="">
            <table border={1} cellPadding={10} cellSpacing={10} width={'70%'}>
              <tbody>
                <tr>
                  <td>STUDENT NAME</td>
                  <td>:</td>
                  <td><input type='text' placeholder='enter student name' /></td>
                </tr>
                <tr>
                  <td>MOBILE NUMBER</td>
                  <td>:</td>
                  <td><input type='number' placeholder='enter mobile number' /></td>
                </tr>
                <tr>
                  <td>COURSE</td>
                  <td>:</td>
                  <td>
                    <select value={course} onChange={handlebranch} width="100%">
                      <option value="1">BTECH</option>
                      <option value="2">DIPLOMA</option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td>BRANCH</td>
                  <td>:</td>
                  <td>
                    <select>
                      {branches.map((data, ind) => (
                        <option key={ind} value={ind}>{data}</option>
                      ))}
                    </select>
                  </td>
                </tr>
                {/* Other fields here */}
              </tbody>
            </table>
          </form>
        </div>
      </center>
    </div>
  );
}

export default Signup;
