package com.exam.service;

import com.exam.model.*;

public class StudentService {

    public Student getStudentFromWeb() {

        // Simulated API response
        Student s = new Student();
        s.setName("R HARISH");
        s.setUsername("STU05O7");
        s.setBatch("2022");
        s.setRegulation("V20");
        s.setBranch("CSE");
        s.setSemester("I");
        s.setSection("D");
        s.setRole("STUDENT");
        
        s.setToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTVFUwNU83Iiwicm9sZSI6IlNUVURFTlQiLCJpYXQiOjE3NzI4MTUwMjcsImV4cCI6MTc3Mjg1MTAyN30.gnYTfLZ9giW2Em9P_AWkryP9W5-YChVNH6BxCZMx314");

        return s;
    }
}
