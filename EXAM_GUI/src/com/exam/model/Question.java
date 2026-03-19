package com.exam.model;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Question {

    public String id;
    public String batch;
    public String exam_type;
    public String branch;
    public String semester;
    public String coursecode;
    public String question_no;
    public String question;
    public String questionurl;
    public List<String> options;
    public List<String> optimgurl;
    public String answer;
    public String selectedopt;
    public String username;
    public Question get(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
    public String getQuestionurl() {
		return questionurl;
	}
	public void setQuestionurl(String questionurl) {
		this.questionurl = questionurl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
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
	public String getQuestion_no() {
		return question_no;
	}
	public void setQuestion_no(String question_no) {
		this.question_no = question_no;
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
	public List<String> getOptimgurl() {
		return optimgurl;
	}
	public void setOptimgurl(List<String> optimgurl) {
		this.optimgurl = optimgurl;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getSelectedopt() {
		return selectedopt;
	}
	public void setSelectedopt(String selectedopt) {
		this.selectedopt = selectedopt;
	}
    

}