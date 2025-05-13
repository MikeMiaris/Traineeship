package com.example.traineeship.domainmodel;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@Column(name = "username", unique = true)
	@NotBlank(message = "Username can not be empty.")
	private String username; //primary key
	
	@Column(name = "studentName")
	@NotBlank(message = "Student Name can not be empty.")
	private String studentName;
	
	@Column(name = "AM")
	@NotBlank(message = "AM can not be empty.")
	private String AM;
	
	@Column(name = "avgGrade")
	private double avgGrade;
	
	@Column(name = "preferredLocation")
	private String preferredLocation;
	
	@Column(name = "interests")
	private String interests;
	
	@Column(name = "skills")
	private String skills;
	
	@Column(name = "lookingForTraineeship")
	private boolean lookingForTraineeship;
	
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "TraineeshipPosition")
    
    @JoinColumn(name = "assigned_position_id")
    private TraineeshipPosition assignedTraineeship; //CHECK THE LOGIC
    
    public Student() {
    	super();
    }
    
    public Student(String username, String studentName, String AM, double avgGrade, String preferredLocation, String interests,
    		String skills, boolean lookingforTraineeship, TraineeshipPosition assignedTraineeship) {
		super();
		this.username = username;
		this.studentName = studentName;
		this.AM = AM;
		this.avgGrade = avgGrade;
		this.preferredLocation= preferredLocation;
		this.interests = interests;
		this.skills = skills;
		this.lookingForTraineeship = lookingforTraineeship;
		this.assignedTraineeship = assignedTraineeship;
	}
	
    //Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAM() {
        return AM;
    }

    public void setAM(String AM) {
        this.AM = AM;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public boolean isLookingForTraineeship() {
        return lookingForTraineeship;
    }

    public void setLookingForTraineeship(boolean lookingForTraineeship) {
        this.lookingForTraineeship = lookingForTraineeship;
    }

    public TraineeshipPosition getAssignedTraineeship() {
        return assignedTraineeship;
    }

    public void setAssignedTraineeship(TraineeshipPosition assignedTraineeship) {
        this.assignedTraineeship = assignedTraineeship;
    }
}
