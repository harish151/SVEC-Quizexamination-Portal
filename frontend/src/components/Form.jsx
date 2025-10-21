import React from 'react'

function FormComponent({
  batch, setBatch,
  branch, setBranch,
  semester, setSemester,
  subjects,
  ccode, setCcode,
  exam_type, setExam_type,
  sections,
  selectedsec,
  setSelectedsec,
  setDisplay,
  buttonname,
  handleregulation,
  handlequestions,
  // subjectText,
  setSubjectText,
}) {
  return (
    <div>
      <form onSubmit={handlequestions}>
        <table align="center" cellPadding={'7px'}>
          <tbody>
            <tr>
              <td>COURSE</td>
              <td>:</td>
              <td>
                <select name="course" id="course">
                  <option value="btech">BTECH</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>BATCH</td>
              <td>:</td>
              <td>
                <select
                  name="batch"
                  id="batch"
                  value={batch}
                  onChange={(e) => {
                    const selectedValue = e.target.value;
                    setBatch(selectedValue);
                    handleregulation(selectedValue,branch);
                    setDisplay(0);
                  }}
                >
                  <option value="-1" disabled>SELECT</option>
                  <option value="2022">2022</option>
                  <option value="2023">2023</option>
                  <option value="2024">2024</option>
                  <option value="2025">2025</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>BRANCH</td>
              <td>:</td>
              <td>
                <select
                  name="branch"
                  id="branch"
                  value={branch}
                  onChange={(e) => {
                                    const selectedbranch = e.target.value;setBranch(e.target.value);
                                    setDisplay(0);
                                    handleregulation(batch,selectedbranch);}}
                >
                  <option value="-1" disabled>SELECT</option>
                  <option value="CSE">COMPUTER SCIENCE AND ENGINEERING</option>
                  <option value="ECE">ELECTRONICS AND COMMUNICATION ENGINEERING</option>
                  <option value="ECT">ELECTRICAL AND ELECTRONICS ENGINEERING (ECT)</option>
                  <option value="EEE">ELECTRICAL AND ELECTRONICS ENGINEERING</option>
                  <option value="ME">MECHANICAL ENGINEERING</option>
                  <option value="CE">CIVIL ENGINEERING</option>
                  <option value="CST">COMPUTER SCIENCE AND TECHNOLOGY</option>
                  <option value="CAI">COMPUTER SCIENCE AND ENGINEERING (AI)</option>
                  <option value="AIM">COMPUTER SCIENCE AND ENGINEERING (AIM)</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>SEMESTER</td>
              <td>:</td>
              <td>
                <select
                  name="semester"
                  id="semester"
                  value={semester}
                  onChange={(e) => {setSemester(e.target.value);setSubjectText(subjects.subjectname[0]);
                    setExam_type(-1);
                  }}
                >
                  <option value="-1" disabled>SELECT</option>
                  <option value="I">I</option>
                  <option value="II">II</option>
                  <option value="III">III</option>
                  <option value="IV">IV</option>
                  <option value="V">V</option>
                  <option value="VI">VI</option>
                  <option value="VII">VII</option>
                  <option value="VIII">VIII</option>
                </select>
              </td>
            </tr>

            <tr>
              <td>SUBJECT</td>
              <td>:</td>
              <td>
                <select name="subject" id="subject-select" value={ccode} onChange={(e)=>{
                  let selectedText = e.target.options[e.target.selectedIndex].text;
                  setCcode(e.target.value);setSubjectText(selectedText);setExam_type(-1);}} >
                    <option value="-1" disabled>SELECT</option>
                  {Array.isArray(subjects.subjectname) && subjects.subjectname.length > 0 ? (
                    subjects.subjectname.map((name, index) => (
                      <option key={index} value={subjects.coursecode?.[index]}>
                        {name}
                      </option>
                    ))
                  ) : (
                    <option disabled>No subjects available</option>
                  )}
                </select>
              </td>
            </tr>
            <tr>
              <td>SECTION</td>
              <td>:</td>
              <td>
              <select name="section" id="section" value={selectedsec} onChange={(e)=>{setSelectedsec(e.target.value);setExam_type(-1);}}>
                {sections.map((i)=>(<option key={i} value={i}>{i==-1?"SELECT":i}</option>))}
              </select></td>
            </tr>
            <tr>
              <td>EXAM</td>
              <td>:</td>
              <td>
                {(ccode && ccode.length >= 3 && ccode[ccode.length - 3] === 'L' || ccode.includes('CSTJE01') || ccode.includes('CSTJE02'))?(
                <select name="exam" id="exam-select" value={exam_type} onChange={(e)=>{setExam_type(e.target.value);setDisplay(0);}}>
                  <option value="-1" disabled>SELECT</option>
                  <option value="INTERNAL">INTERNAL</option>
                  <option value="EXTERNAL">EXTERNAL</option>
                </select>
                ):
                (<select name="exam" id="exam-select" value={exam_type} onChange={(e)=>{setExam_type(e.target.value);setDisplay(0);}}>
                  <option value="-1" disabled>SELECT</option>
                  <option value="MID-1">MID-1</option>
                  <option value="MID-2">MID-2</option>
                </select>
                )}
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colSpan={3} align='center' style={{paddingTop:'20px'}}><button type='submit' className='button'>{buttonname}</button></td>
            </tr>
          </tfoot>
        </table>
      </form>
    </div>
  )
}

export default FormComponent;