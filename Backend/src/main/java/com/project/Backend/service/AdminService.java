package com.project.Backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.Backend.model.Schedule;
import com.project.Backend.repository.ScheduleRepo;

@Service
public class AdminService {

	public List<Schedule> getschedule(ScheduleRepo sr, String branch, String semester, String coursecode, String subject, String exam_type) {
		return sr.findByBranchAndSemesterAndCoursecodeAndSubjectAndExamtype(branch,semester,coursecode,subject,exam_type);
		
	}

	public void updateschedule(ScheduleRepo sr, Schedule sc) {
		Schedule s = new Schedule();
		s.setId(sc.getId());
		s.setBranch(sc.getBranch());
		s.setExamtype(sc.getExamtype());
		s.setSemester(sc.getSemester());
		s.setCoursecode(sc.getCoursecode());
		s.setSubject(sc.getSubject());
		s.setDate(sc.getDate());
		s.setStartTime(sc.getStartTime());
		s.setEndTime(sc.getEndTime());
		sr.save(s);
	}
	
	
}
