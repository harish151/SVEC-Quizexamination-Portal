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
	
	@GetMapping("/getresults")
	public List<Result> getresults(@RequestParam("batch") String batch,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type,@RequestParam("semester") String semester,@RequestParam("section") String section,@RequestParam("username") String u) {
		return cfs.getresults(batch,branch,code,type,semester,section,u);
	}
	
	@PostMapping("/setresults")
	public void setresults(@RequestBody Result r) {
		cfs.setresults(r);
	}
	
	@Autowired
	ScheduleRepo schr;
	
	@GetMapping("/getschedule")
	public List<Schedule> getschedule(@RequestParam("branch") String branch,@RequestParam("semester") String semester){
		return cfs.getschedule(schr,branch,semester);
	}
}
