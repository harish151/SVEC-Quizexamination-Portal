import './App.css'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login'
import Signup from './components/Signup';
import Student from './components/Student';
import Employee from './components/Employee';
import Instructions from './components/Instructions';
import Exam from './components/Exam';

function App() {

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/student" element={<Student />} />
        <Route path="/employee" element={<Employee />} />
        <Route path="/instructions" element={<Instructions />} />
        <Route path="/exam" element={<Exam />} />
      </Routes>
    </Router>

  )
}

export default App
