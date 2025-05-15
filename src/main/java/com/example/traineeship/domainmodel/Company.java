package com.example.traineeship.domainmodel;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity 
@Table(name="companies")
public class Company {
	
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "companyName")
	@NotBlank(message = "Company name cannot be blank.")
	private String companyName;
	
	@Column(name = "companyLocation")
	@NotBlank(message = "Company location cannot be blank.")
	private String companyLocation;
	
	// D: cascade type is still SQL related
	// manages this list on delete/update
	// lazy fetch means we better control data retrieved
	// mappedBy on both makes relationship bidirectional (in t_p it's going to be mapped by companyName/id)
	// joinColumn logic same as student, example doesn't follow this logic (unclear)
	@OneToMany(	cascade = CascadeType.ALL,
				fetch = FetchType.LAZY,
				orphanRemoval = true,
				mappedBy="company")
	@JoinColumn(name = "position_id") // CHECK!!!!
	private List<TraineeshipPosition> positions ;
	
	
	//constructors
	public Company() {
		super();
	}
	
	public Company(String username, String companyName, String companyLocation) {
		super();
		this.username = username;
		this.companyName = companyName;
		this.companyLocation = companyLocation;
		this.positions = new ArrayList<>();
	}
	
	
    //Getters and Setters
    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getCompanyName(){
        return companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyLocation(){
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation){
        this.companyLocation = companyLocation;
    }

    public List<TraineeshipPosition> getPositions(){
        return positions;
    }

    public void setPositions(List<TraineeshipPosition> positions){
        this.positions = positions;
    }
	
	// D: Functions to implement User Stories
    
    // anything that implements positions needs rework after traineeships get changed
    
    // D: Function that gets non occupied positions
    public List<TraineeshipPosition> nonTakenPositions(){
    	List<TraineeshipPosition> nonTaken = new ArrayList<>();
    	for (int i = 0; i < positions.size(); i++) {
    		if (!positions.get(i).isAssigned()) {
    			nonTaken.add(positions.get(i));
    		}
    	}
    	return nonTaken;
    }
    
    // D: Function that gets occupied positions
    public List<TraineeshipPosition> takenPositions(){
    	List<TraineeshipPosition> taken = new ArrayList<>();
    	for (int i = 0; i < positions.size(); i++) {
    		if (positions.get(i).isAssigned()) {
    			taken.add(positions.get(i));
    		}
    	}
    	return taken;
    }
    
    // D: Function that adds a new position
    // assume that dates are given as array of ints (Y, M, D)
    public void announcePosition(String title, String description, int[] startDate, int[] endDate, String topics, String skills) {
    	LocalDate fromDate = LocalDate.of(startDate[0], startDate[1], startDate[2]);
    	LocalDate toDate = LocalDate.of(endDate[0], endDate[1], endDate[2]);
    	TraineeshipPosition newPos = new TraineeshipPosition(title, description, fromDate, toDate, topics, skills);
    	positions.add(newPos);
    }
    
    // D: Function that deletes a position based on title
    public void removePosition(String title) {
    	for (int i = 0; i < positions.size(); i++) {
    		if (positions.get(i).getTitle().equals(title)) {
    			positions.remove(i);
    		}
    	}
    }
    
    // D: Function that adds an evaluation on a position
    public void evaluatePosition(String title, int motivation, int efficiency, int effectiveness) {
    	for (int i = 0; i < positions.size(); i++) {
    		if (positions.get(i).getTitle().equals(title)) {
    			// D: just add an evaluation here, as Company
    			positions.get(i).addEvaluation(Role.COMPANY, motivation, efficiency, effectiveness);
    		}
    	}
    }
}
