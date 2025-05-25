package com.example.traineeship.domainmodel;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;


@Entity
@Table(name="professors")
public class Professor {

	@Id	
	@Column(name = "username")
	private String username;
	
	@Column(name = "professorName")
	private String professorName;
	
	@Column(name = "interests")
	private String interests;
	
	@OneToMany(cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			orphanRemoval = true,
			mappedBy="supervisor")
	List<TraineeshipPosition> supervisedPositions;
    
	//Constructors
	public Professor() {
		super();
	}
	
	public Professor(String username, String professorName, String interests) {
		
		this.username = username;
		this.professorName = professorName;
		this.interests = interests;
		this.supervisedPositions = new ArrayList<>();
		
	}
	
	//Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public List<TraineeshipPosition> getSupervisedPositions() {
        return supervisedPositions;
    }

    public void setSupervisedPositions(List<TraineeshipPosition> supervisedPositions) {
        this.supervisedPositions = supervisedPositions;
    }
    
    
    public void addSupervisedPosition(TraineeshipPosition position) {
    	supervisedPositions.add(position);
    	position.setSupervisor(this);
    }
    
}
