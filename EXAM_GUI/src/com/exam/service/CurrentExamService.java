package com.exam.service;
import com.exam.model.*;

public class CurrentExamService {

	public CurrentExam GetCurrentExam() {
		// Simulated API response
		CurrentExam exam = new CurrentExam();
		exam.setId("EXAM123");
		exam.setExamtype("MID-1");
		exam.setBranch("CSE");
		exam.setSemester("I");
		exam.setCoursecode("V20MAT01");
		exam.setSubject("LINEAR ALGEBRA AND DIFFERNTIAL EQUATIONS");
		exam.setDate("28-02-2026");
		exam.setStartTime("10:00 AM");
		exam.setEndTime("12:00 PM");
		return exam;

	}

}
