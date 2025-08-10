package com.project.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Backend.model.Schedule;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.service.AdminServiceConsumer;

@RestController
public class AdminController {
	
	@Autowired
	AdminServiceConsumer as;
	
	@Autowired
	ScheduleRepo sr;
	
	@GetMapping("/admin/getschedule")
	public List<Schedule> getschedule(@RequestParam("branch") String branch,@RequestParam("semester") String semester,@RequestParam("coursecode") String coursecode,@RequestParam("subject") String subject,@RequestParam("exam_type") String exam_type) {
		return as.getschedule(sr,branch,semester,coursecode,subject,exam_type);
	}
	
	@PostMapping("/admin/updateschedule")
	public void updateschedule(@RequestBody Schedule sc) {
		as.updateschedule(sr,sc);
	}
}
