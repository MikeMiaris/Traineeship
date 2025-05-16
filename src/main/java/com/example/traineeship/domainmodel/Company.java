package com.example.traineeship.domainmodel;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

import jakarta.persistence.*;


@Entity 
@Table(name="companies")
public class Company {
	
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name = "companyLocation")
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
	
	// D: Functions to implement User Stories -- PROBABLY IN SERVICES
    
    // anything that implements positions needs rework after traineeships get changed
    
    // D: Function that adds a new position
    public void announcePosition(TraineeshipPosition position) {
    	positions.add(position);
    }
    
    // D: Function that deletes a position based on title -- might not need TODO
    public void removePosition(Integer id) {
    	for (int i = 0; i < positions.size(); i++) {
    		if (positions.get(i).getId().equals(id)) {
    			positions.remove(i);
    		}
    	}
    }
    
    // D: Function that adds an evaluation on a position -- don't need? TODO
    public void evaluatePosition(String title, int motivation, int efficiency, int effectiveness) {
    	for (int i = 0; i < positions.size(); i++) {
    		if (positions.get(i).getTitle().equals(title)) {
    			// D: just add an evaluation here, as Company
    			positions.get(i).addEvaluation(Role.COMPANY, motivation, efficiency, effectiveness);
    		}
    	}
    }
}
