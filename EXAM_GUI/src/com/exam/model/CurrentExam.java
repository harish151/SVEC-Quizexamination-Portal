package src.com.exam.model;

public class CurrentExam {
    private String id;
	private String examtype;
	private String branch;
	private String semester;
	private String coursecode;
	private String subject;
	private String date;
	private String startTime;
	private String endTime;

    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getExamtype() {
        return examtype;
    }
    public void setExamtype(String examtype) {
        this.examtype = examtype;
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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
