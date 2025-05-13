package com.example.traineeship.domainmodel;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

@Entity 
@Table(name="positions")
public class TraineeshipPosition {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "fromDate")
	private LocalDate fromDate;
	
	@Column(name = "toDate")
	private LocalDate toDate;
	
	@Column(name = "topics")
	private String topics;
	
	@Column(name = "skills")
	private String skills;
	
	@Column(name = "isAssigned")
	private boolean isAssigned;
	
	@Column(name = "studentLogbook")
	private String studentLogbook; //!!!---!!!
	
	@Column(name = "passFailGrade")
	private boolean passFailGrade;
	
	//CHECK CHECK
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "professor_id")
	private Professor supervisor;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	@OneToMany(mappedBy = "traineeshipPosition", //CHECK
	           cascade = CascadeType.ALL,
	           orphanRemoval = true)
	private List<Evaluation> evaluations;

    public TraineeshipPosition(){
    	
    }

    public TraineeshipPosition(String title, String description, LocalDate fromDate, LocalDate toDate, String topics, String skills){
        this.title = title;
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.topics = topics;
        this.skills = skills;
        //defaults
        this.isAssigned = false;
        this.passFailGrade = false;
        this.studentLogbook = "";
    }
	
	
	
    //Getters and Setters
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public LocalDate getFromDate(){
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate){
        this.fromDate = fromDate;
    }

    public LocalDate getToDate(){
        return toDate;
    }

    public void setToDate(LocalDate toDate){
        this.toDate = toDate;
    }

    public String getTopics(){
        return topics;
    }

    public void setTopics(String topics){
        this.topics = topics;
    }

    public String getSkills(){
        return skills;
    }

    public void setSkills(String skills){
        this.skills = skills;
    }

    public boolean isAssigned(){
        return isAssigned;
    }

    public void setAssigned(boolean isAssigned){
        this.isAssigned = isAssigned;
    }

    public String getStudentLogbook(){
        return studentLogbook;
    }

    public void setStudentLogbook(String studentLogbook) {
        this.studentLogbook = studentLogbook;
    }

    public boolean isPassFailGrade(){
        return passFailGrade;
    }

    public void setPassFailGrade(boolean passFailGrade){
        this.passFailGrade = passFailGrade;
    }

    public Student getStudent(){
        return student;
    }

    public void setStudent(Student student){
        this.student = student;
    }

    public Professor getSupervisor(){
        return supervisor;
    }

    public void setSupervisor(Professor supervisor){
        this.supervisor = supervisor;
    }

    public Company getCompany(){
        return company;
    }

    public void setCompany(Company company){
        this.company = company;
    }

    public List<Evaluation> getEvaluations(){
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations){
        this.evaluations = evaluations;
    }
}
