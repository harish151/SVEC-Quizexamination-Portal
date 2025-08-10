package com.project.Backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.repository.ResultRepo;
import com.project.Backend.repository.ScheduleRepo;

@Service
public class CommonFuncServicesConsumer {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	ResultRepo rr;


	public String uploadImage(MultipartFile file, String imgbbApiKey) {
        try {
            byte[] bytes = file.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("https://api.imgbb.com/1/upload");
            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("key", imgbbApiKey));
            params.add(new BasicNameValuePair("image", encodedImage));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = httpClient.execute(httpPost);
            String json = EntityUtils.toString(response.getEntity());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            return root.path("data").path("image").path("url").asText();
        } catch (IOException e) {
            return null;
        }
    }
	
	
	public List<Result> getresults(String batch, String branch, String code, String type, String semester, String section,
			String u) {
		 List<Result> r = rr.findByBatchAndBranchAndCoursecodeAndExamTypeAndSemesterAndSectionAndUsername(batch, branch, code, type, semester, section, u);
		 
		 return r;
	}
	
	@KafkaListener(topics = "upload-result-topic", groupId = "quiz-group")
	public void uploadresults(String message) {
	    try {
	        Map<String, Object> data = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});
	        Result r = new Result();
	        r.setBatch((String) data.get("batch"));
	        r.setUsername((String) data.get("username"));
	        r.setBranch((String) data.get("branch"));
	        r.setSemester((String) data.get("semester"));
	        r.setCoursecode((String) data.get("coursecode"));
	        r.setExamType((String) data.get("examtype"));
	        r.setSection((String) data.get("section"));
	        List<String> originalans = (List<String>) data.get("originalans");
	        List<String> attemptedans = (List<String>) data.get("attemptedans");
	        double marks = 0.0;
	        for (int i = 0; i < Math.min(originalans.size(), attemptedans.size()); i++) {
	            String origAns = originalans.get(i);
	            String attAns = attemptedans.get(i);
	            if (origAns != null && attAns != null && origAns.trim().equalsIgnoreCase(attAns.trim())) {
	                marks += 0.5;
	            }
	        }
	        r.setMarks(Math.ceil(marks));
	        rr.save(r);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
	public List<Schedule> getschedule(ScheduleRepo schr, String branch, String semester) {
		List<Schedule> sh = schr.findByBranchAndSemester(branch,semester);
		return sh;
	}

	
}
