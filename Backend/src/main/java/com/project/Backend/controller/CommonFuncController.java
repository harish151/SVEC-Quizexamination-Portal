package com.project.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.service.CommonFuncServices;

//@CrossOrigin(
//"*"
////origins = "http://localhost:3000",
////allowedHeaders = "*",
////exposedHeaders = "Authorization",
////allowCredentials = "true"
//)

@RestController
public class CommonFuncController {
	
	@Autowired
	CommonFuncServices cfs = new CommonFuncServices();
	
	@GetMapping("/common/getresults")
	public List<Result> getresults(@RequestParam("batch") String batch,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type,@RequestParam("semester") String semester,@RequestParam("section") String section,@RequestParam("username") String u) {
		return cfs.getresults(batch,branch,code,type,semester,section,u);
	}
	
	@PostMapping("/common/uploadresults")
	public void uploadresults(@RequestBody Result r,@RequestParam("originalans") List<String> originalans,@RequestParam("attemptedans") List<String> attemptedans) {
//		System.out.println(originalans);
//		System.out.println(attemptedans);
		cfs.uploadresults(r,originalans,attemptedans);
	}
	
	@Autowired
	ScheduleRepo schr;
	
	@GetMapping("/common/getschedule")
	public List<Schedule> getschedule(@RequestParam("branch") String branch,@RequestParam("semester") String semester){
		return cfs.getschedule(schr,branch,semester);
	}
}
