package com.project.Backend.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Schedule;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.StudentRepo;
import com.project.Backend.service.StudentServices;

//@CrossOrigin(
//"*"
////origins = "http://localhost:3000",
////allowedHeaders = "*",
////exposedHeaders = "Authorization",
////allowCredentials = "true"
//)

@RestController
public class StudentController {
	
	@Autowired
	StudentServices stus;
	
	@Autowired
	StudentRepo sturepo;
	
	
	@PostMapping("/noauth/createstu")
	public String createstu(@RequestParam("name") String name,@RequestParam("username") String username,@RequestParam("batch") String batch,@RequestParam("regulation") String regulation,@RequestParam("branch") String branch,@RequestParam("semester") String semester,@RequestParam("section") String section,@RequestParam(value="image",required = false) MultipartFile image,@RequestParam("role") String role) {
		return stus.createstu(sturepo, name,username,batch,regulation,branch,semester,section,image,role);
	}
	
	@GetMapping("/noauth/loginstu")
	public HashMap<String,Object> loginstu(@RequestParam("username") String username,@RequestParam("password") String password) {
		return stus.loginstu(sturepo, username.toUpperCase(),password);
	}
	
	@Autowired
	ScheduleRepo schr;
	
	@GetMapping("/student/getexams")
	public List<Schedule> getexams(@RequestParam("branch") String branch,@RequestParam("semester") String semester,@RequestParam("date") String date){
		return stus.getexams(schr,branch,semester,date);
	}
	
	@Autowired
	QuestionsRepo qr;
	
	@GetMapping("/student/examquestions")
	public List<Questions> findallexamquestions(@RequestParam("batch") String year,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("examtype") String type)
	{
		List<Questions> q = stus.getAllexamQuestions(qr,year,type,branch,code);
		return q;
	}
	

}
