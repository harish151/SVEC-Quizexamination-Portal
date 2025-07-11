import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import './App.css'
import Login from './components/Login'
import Student from './components/Student'
import React from 'react'
import Instructions from './components/Instructions'
import Exam from './components/Exam'
import Employee from './components/Employee'

function App() {

  return (
      <Router>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/student" element={<Student />} />
          <Route path="/employee" element={<Employee />} />
          <Route path="/instructions" element={<Instructions />} />
          <Route path="/exam" element={<Exam />} />
        </Routes>
      </Router>
  )
}

export default App
