package com.project.Backend.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.Backend.controller.EmployeeController;
import com.project.Backend.model.Questions;
import com.project.Backend.model.Regulation;
import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.model.Subjects;
import com.project.Backend.model.Teachers;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.RegulationRepo;
import com.project.Backend.repository.ResultRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.SubjectsRepo;
import com.project.Backend.repository.TeacherRepo;
import com.project.Backend.security.JwtUtil;

@Service
public class EmployeeServicesConsumer {
	
	@Value("${imgbb.api.key}")
    private String imgbbApiKey;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
    private final TeacherRepo teacherrepo;
    private final CommonFuncServicesConsumer cfsc;
    private final RegulationRepo rr;
    private final ResultRepo rr1;
    private final SubjectsRepo sr;
    private final QuestionsRepo qr;
    private final ScheduleRepo schr;
    
    public EmployeeServicesConsumer(TeacherRepo teacherrepo,CommonFuncServicesConsumer cfsc,RegulationRepo rr,ResultRepo rr1,SubjectsRepo sr,QuestionsRepo qr,ScheduleRepo schr) {
        this.teacherrepo = teacherrepo;
        this.cfsc = cfsc;
        this.rr = rr;
        this.rr1 = rr1;
        this.sr=sr;
        this.qr=qr;
        this.schr=schr;
    }
	
    @KafkaListener(topics = "employee-create-topic", groupId = "quiz-group")
    public void createemp(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});

            Teachers t = new Teachers();
            t.setName((String) data.get("name"));
            t.setUsername((String) data.get("username"));
            t.setBranch((String) data.get("branch"));
            t.setTeachsubjects((List<String>) data.get("teachsub"));
            t.setRole((String) data.get("role"));

            if (data.containsKey("image")) {
                String base64Image = (String) data.get("image");
                MultipartFile file = base64ToMultipartFile(base64Image, "employee.jpg");
                String imageUrl = cfsc.uploadImage(file, imgbbApiKey);
                t.setImage(imageUrl);
            }

            teacherrepo.save(t);
            System.out.println("Employee created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private MultipartFile base64ToMultipartFile(String base64, String filename) throws IOException {
	    byte[] decoded = Base64.getDecoder().decode(base64);
	    return new MockMultipartFile(filename, filename, "image/jpeg", decoded);
	}

	
	public HashMap<String,Object> loginemp(TeacherRepo teacherrepo, String username, String password) {
		List<Teachers> t = teacherrepo.findByUsernameAndPassword(username, password);
		if(!t.isEmpty()) {  //if document is present
				Teachers teacher = t.get(0);        
				String role = teacher.getRole();
				JwtUtil jw = new JwtUtil();
				HashMap<String,Object> hm = new HashMap<>();
				//String r = (String) t.get("role");
				hm.put("token", jw.generateToken(username,role));
				hm.put("details", t);
				return hm; 
		}
		else {
			return null; //there is no document
		}
	}
	
	public String checkeligibility(TeacherRepo teacherrepo,String username,String coursecode) {
		Teachers u = teacherrepo.findByUsernameAndTeachsubjects(username,coursecode);
		if(u!=null) {
			return "eligible";
		}
		else {
			return "noteligible";
		}
	}
	
	@KafkaListener(topics = "set-regulation-topic", groupId = "quiz-group")
	public void setregulation(String message) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Regulation reg = objectMapper.readValue(message, Regulation.class);
	        rr.save(reg);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Regulation> getregulation(RegulationRepo rr,String batch,String branch) {
		List<Regulation> reg = rr.findByBatchAndBranch(batch,branch);
		if(reg == null)	
			return null;
		else 
			return reg;
	}
	
	@KafkaListener(topics = "post-subjects-topic", groupId = "quiz-group")
	public void postsubjects(String message) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Subjects sub = objectMapper.readValue(message, Subjects.class);
	        sr.save(sub);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Subjects> getsubjects(SubjectsRepo sr, String reg, String branch, String sem) {
		List<Subjects> s = sr.findByRegulationAndBranchAndSemester(reg,branch,sem);
		return s;
	}
	
	@KafkaListener(topics = "add-question-topic", groupId = "quiz-group")
	public void createquestion(String message) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Questions que = objectMapper.readValue(message, Questions.class);
	        que.setBatch(que.getBatch());
			que.setExam_type(que.getExam_type());
			que.setBranch(que.getBranch());
			que.setSemester(que.getSemester());
			que.setCoursecode(que.getCoursecode());
			que.setQuestion_no(que.getQuestion_no());
			que.setQuestion(que.getQuestion().trim());
			List<String> options = que.getOptions();
		    options.replaceAll(String::trim);
		    que.setOptions(options);
		    que.setAnswer(que.getAnswer().trim());
	        qr.save(que);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public int updatequestion(QuestionsRepo qr, Questions q) {
	    Optional<Questions> optional = qr.findById(q.getId());
	    if (optional.isPresent()) {
	        Questions existing = optional.get();
	        existing.setBatch(q.getBatch());
	        existing.setExam_type(q.getExam_type());
	        existing.setBranch(q.getBranch());
	        existing.setSemester(q.getSemester());
	        existing.setQuestion_no(q.getQuestion_no());
	        existing.setQuestion(q.getQuestion().trim());
	        List<String> options = q.getOptions();
		    options.replaceAll(String::trim);
		    existing.setOptions(options);
	        existing.setAnswer(q.getAnswer().trim());
	        qr.save(existing);
	        return 1;
	    } else {
	    	return 2;
	    }
	}
	
	public List<Questions> getAllQuestions(String year, String type, String branch,String code) {
		return qr.findQuestions(year,type,branch,code);
		
	}
	
	@KafkaListener(topics = "get-noofqueposted-topic", groupId = "quiz-group")
    public void handleGetNoOfQuePosted(String message) {
        try {
            Map<String, Object> data = objectMapper.readValue(message, new TypeReference<>() {});
            String reqId = (String) data.get("id");
            String batch = (String) data.get("batch");
            String branch = (String) data.get("branch");
            String coursecode = (String) data.get("coursecode");
            String examtype = (String) data.get("examtype");
            List<Questions> questions = qr.findQuestions(batch,examtype, branch, coursecode);
            int count = questions.size();
            String jsonResponse = objectMapper.writeValueAsString(count);
            kafkaTemplate.send("get-noofqueposted-response", reqId, jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@KafkaListener(topics = "delete-question-topic", groupId = "quiz-group")
	public void deleteQuestion(String message) {
		try {
			if(qr.existsById(message)) {
				qr.deleteById(message);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = "add-schedule-topic", groupId = "quiz-group")
	public void addschedule(String message) {
		try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        Schedule s = objectMapper.readValue(message, Schedule.class);
	        schr.save(s);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@KafkaListener(topics = "all-sturesults-topic", groupId = "quiz-group")
	public void getresultswithoutusername(String message) {
		Map<String, Object> data;
		try {
			data = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});
			String reqId = (String) data.get("id");
			String batch = (String) data.get("batch");
			String branch = (String) data.get("branch");
			String coursecode = (String) data.get("coursecode");
			String examtype = (String) data.get("examtype");
			String semester = (String) data.get("semester");
			String section = (String) data.get("section");
			List<Result>  res = rr1.findByBatchAndBranchAndCoursecodeAndExamTypeAndSemesterAndSection(batch, branch, coursecode, examtype, semester, section);
			res.sort(Comparator.comparing(Result::getUsername));
			String jsonResponse = objectMapper.writeValueAsString(res);
			kafkaTemplate.send("all-sturesults-response",reqId,jsonResponse);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
