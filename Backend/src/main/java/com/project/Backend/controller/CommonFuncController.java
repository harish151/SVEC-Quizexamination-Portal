package com.project.Backend.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Backend.kafka.KafkaProducerService;
import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.service.CommonFuncServicesConsumer;

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
	CommonFuncServicesConsumer cfs = new CommonFuncServicesConsumer();
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final KafkaProducerService kafkaProducerService;
	
	public CommonFuncController(KafkaProducerService kafkaProducerService) {
	    this.kafkaProducerService = kafkaProducerService;
	    
	}
	
	@GetMapping("/common/getresults")
	public List<Result> getresults(@RequestParam("batch") String batch,@RequestParam("branch") String branch,@RequestParam("coursecode") String code,@RequestParam("exam_type") String type,@RequestParam("semester") String semester,@RequestParam("section") String section,@RequestParam("username") String u) {
		return cfs.getresults(batch,branch,code,type,semester,section,u);
	}
	
	@PostMapping("/common/uploadresults")
	public String uploadresults(@RequestBody Result r) {
	    try {
	        HashMap<String, Object> kafkaData = new HashMap<>();
	        kafkaData.put("branch", r.getBranch());
	        kafkaData.put("batch", r.getBatch());
	        kafkaData.put("coursecode", r.getCoursecode());
	        kafkaData.put("examtype", r.getExamType());
	        kafkaData.put("section", r.getSection());
	        kafkaData.put("semester", r.getSemester());
	        kafkaData.put("username", r.getUsername());
	        kafkaData.put("originalans", r.getOriginalans());
	        kafkaData.put("attemptedans", r.getAttemptedans());

	        String jsonMessage = objectMapper.writeValueAsString(kafkaData);
	        kafkaProducerService.sendMessage("upload-result-topic", jsonMessage);

	        return "successfully result uploaded";
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Failed to upload result";
	    }
	}

	
	@Autowired
	ScheduleRepo schr;
	
	@GetMapping("/common/getschedule")
	public List<Schedule> getschedule(@RequestParam("branch") String branch,@RequestParam("semester") String semester){
		return cfs.getschedule(schr,branch,semester);
	}
}
