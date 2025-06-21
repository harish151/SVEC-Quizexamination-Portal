package com.project.Backend.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exam_questions")
public class Questions {
	@Id
	String id;
	String acedemic_year;
	String exam_type;
	String branch;
	String semester;
	String coursecode;
	String subject;
	String question;
	List<String> options;
	String answer;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAcedemic_year() {
		return acedemic_year;
	}
	public void setAcedemic_year(String acedemic_year) {
		this.acedemic_year = acedemic_year;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	public String getCoursecode() {
		return coursecode;
	}
	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "Questions [acedemic_year=" + acedemic_year + ", exam_type=" + exam_type + ", branch=" + branch
				+ ", semester=" + semester + ", question=" + question + ", options=" + options + ", answer=" + answer
				+ "] "+id;
	}
	
	
	
	
}
