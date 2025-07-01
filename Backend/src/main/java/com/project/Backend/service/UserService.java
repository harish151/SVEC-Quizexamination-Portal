package com.project.Backend.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Backend.model.Questions;
import com.project.Backend.model.Regulation;
import com.project.Backend.model.Result;
import com.project.Backend.model.Schedule;
import com.project.Backend.model.Subjects;
import com.project.Backend.model.User;
import com.project.Backend.repository.QuestionsRepo;
import com.project.Backend.repository.RegulationRepo;
import com.project.Backend.repository.Repo1;
import com.project.Backend.repository.ResultRepo;
import com.project.Backend.repository.ScheduleRepo;
import com.project.Backend.repository.SubjectsRepo;

@Service
public class UserService{
	
	ArrayList<String> result = new ArrayList<>();

	public String create(Repo1 r,User u) {
		try {
			r.save(u);
			return "success";
		}
		catch(Exception e) {
			return "failed";
		}
	}

	public Optional<User> login(Repo1 r, String username,String password) {
		Optional<User> l = r.findByUsernameAndPassword(username, password);
		if(l!=null) {  //if document is present
				return l; 
		}
		else {
			System.out.println(l);
			return l; //there is no document
		}
	}
	
	public String checkeligibility(Repo1 r,String username,String coursecode) {
		User u = r.findByUsernameAndTeachsub(username,coursecode);
		if(u!=null) {
			return "eligible";
		}
		else {
			return "noteligible";
		}
	}
	
	public String setregulation(RegulationRepo rr,Regulation reg) {
		rr.save(reg);
		return reg.toString();
	}
	
	public List<Regulation> getregulation(RegulationRepo rr,String batch,String branch) {
		List<Regulation> reg = rr.findByBatchAndBranch(batch,branch);
		if(reg == null)	
			return null;
		else 
			return reg;
	}
	
	public String postsubjects(SubjectsRepo sr, Subjects s) {
		Subjects sub = sr.save(s);
		return sub.toString();
		}
	
	public List<Subjects> getsubjects(SubjectsRepo sr, String reg, String branch, String sem) {
		List<Subjects> s = sr.findByRegulationAndBranchAndSemester(reg,branch,sem);
		return s;
	}
	
	public String createquestion(QuestionsRepo qr,Questions q) {
		qr.save(q);
		return q.getId();
	}

	public int updatequestion(QuestionsRepo qr, Questions q) {
	    Optional<Questions> optional = qr.findById(q.getId());
	    if (optional.isPresent()) {
	        Questions existing = optional.get();
	        existing.setBatch(q.getBatch());
	        existing.setExam_type(q.getExam_type());
	        existing.setBranch(q.getBranch());
//	        existing.setSubject(q.getSubject());
	        existing.setSemester(q.getSemester());
	        existing.setQuestion_no(q.getQuestion_no());
	        existing.setQuestion(q.getQuestion());
	        existing.setOptions(q.getOptions());
	        existing.setAnswer(q.getAnswer());
	        qr.save(existing);
	        return 1;
	    } else {
	    	return 2;
	    }
	}

	public List<Questions> getAllQuestions(QuestionsRepo qr, String year, String type, String branch,String code) {
		return qr.findQuestions(year,type,branch,code);
		
	}

	public String deleteQuestion(QuestionsRepo qr,String id) {
		if(qr.existsById(id)) {
			qr.deleteById(id);
			return "deleted";
		}
		else {
			return "id not found";
		}
	}

	public String addschedule(ScheduleRepo schr, Schedule sch) {
		schr.save(sch);
		return sch.toString();
	}

	public List<Schedule> getschedule(ScheduleRepo schr, String branch, String semester) {
		List<Schedule> sh = schr.findByBranchAndSemester(branch,semester);
		return sh;

	}
	
	public List<Schedule> getexams(ScheduleRepo schr, String branch, String semester, String date) {
		List<Schedule> sh = schr.findByBranchAndSemesterAndDate(branch,semester,date);
		return sh;
	}

	public List<Questions> getAllexamQuestions(QuestionsRepo qr, String year, String type, String branch, String code) {
		List<Questions> q =qr.findQuestions(year,type,branch,code);
		return shuffleQuestion(q);
	}

	public List<Questions> shuffleQuestion(List<Questions> q) {
		for (Questions que : q) {
	        String correct = que.getAnswer();
	        List<String> options = que.getOptions();
	        Collections.shuffle(options);
	        que.setOptions(options);
	        for (String opt : options) {
	            if (opt.equals(correct)) {
	                result.add(correct);
	                break;
	            }
	        }
		}
		return q;
	}

	@Autowired
	ResultRepo rr;
	
	public void setresults(Result r) {
		double marks = 0.0;
		List<String> studentAnswers = r.getAns();
//		System.out.println(studentAnswers);
//		System.out.println(result);
		int size = Math.min(studentAnswers.size(), result.size());
		
	    for (int i = 0; i < size; i++) {
	        if (studentAnswers.get(i) != null && studentAnswers.get(i).equals(result.get(i))) {
	            marks += 0.5;
	        }
	    }
		marks = Math.ceil(marks);
		r.setMarks(marks);
		r.setAns(null);
		rr.save(r);
		result.clear();
		
	}

	public List<Result> getresults(String batch, String branch, String code, String type, String semester, String section,
			String u) {
		return rr.findByBatchAndBranchAndCoursecodeAndExamTypeAndSemesterAndSectionAndUsername(batch, branch, code, type, semester, section, u);
	}

	public List<Result> getresultswithoutusername(String batch, String branch, String code, String type,
			String semester, String section) {
		return rr.findByBatchAndBranchAndCoursecodeAndExamTypeAndSemesterAndSection(batch, branch, code, type, semester, section);
	}
}
