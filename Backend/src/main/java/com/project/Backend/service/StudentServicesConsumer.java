package com.project.Backend.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Backend.model.Questions;
import com.project.Backend.model.Schedule;
import com.project.Backend.model.Students;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.StudentRepo;
import com.project.Backend.security.JwtUtil;

@Service
public class StudentServices {
	
	@Value("${imgbb.api.key}")
    private String imgbbApiKey;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
    private final StudentRepo studentRepo;
    
    public StudentServices(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
	
	@KafkaListener(topics = "student-create-topic", groupId = "quiz-group")
    public void createstu(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});

            Students student = new Students();
            student.setName((String) data.get("name"));
            student.setUsername((String) data.get("username"));
            student.setBatch((String) data.get("batch"));
            student.setRegulation((String) data.get("regulation"));
            student.setBranch((String) data.get("branch"));
            student.setSemester((String) data.get("semester"));
            student.setSection((String) data.get("section"));
            student.setRole((String) data.get("role"));

            if (data.containsKey("image")) {
                String base64Image = (String) data.get("image");
                MultipartFile file = base64ToMultipartFile(base64Image, "student.jpg");
                String imageUrl = new CommonFuncServices().uploadImage(file, imgbbApiKey);
                student.setImage(imageUrl);
            }
            studentRepo.save(student);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private MultipartFile base64ToMultipartFile(String base64, String filename) throws IOException {
	    byte[] decoded = Base64.getDecoder().decode(base64);
	    return new MockMultipartFile(filename, filename, "image/jpeg", decoded);
	}

	
	public HashMap<String, Object> loginstu(StudentRepo sturepo, String username, String password) {
		List<Object> l = sturepo.findByUsernameAndPassword(username, password);
		if(!l.isEmpty()) {  //if document is present
				Students stu = (Students) l.get(0);        
				String role = stu.getRole();
				JwtUtil jw = new JwtUtil();
				HashMap<String,Object> hm = new HashMap<>();
				hm.put("token", jw.generateToken(username,role));
				hm.put("details", l);
				return hm; 
		}
		else {
			return null; //there is no document
		}
	}
	
	public List<Schedule> getexams(ScheduleRepo schr, String branch, String semester, String date) {
		List<Schedule> sh = schr.findByBranchAndSemesterAndDate(branch,semester,date);
		return sh;
	}
	
	public List<Questions> getAllexamQuestions(QuestionsRepo qr, String year, String type, String branch, String code) {
		List<Questions> q =qr.findQuestions(year,type,branch,code);
		return shuffleQuestions(q);
	}

	public List<Questions> shuffleQuestions(List<Questions> q) {
		Collections.shuffle(q);
		for (Questions que : q) {
            Collections.shuffle(que.getOptions());
        }
		return q;
	}
}
