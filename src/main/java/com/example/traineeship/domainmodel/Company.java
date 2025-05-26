package com.example.traineeship.domainmodel;

import java.util.List;
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
				mappedBy="company")
	//@JoinColumn(name = "position_id")
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
	
	// D: Functions needed by Services to implement User Stories
    
    // D: Function that adds a new position
    public void announcePosition(TraineeshipPosition position) {
    	positions.add(position);
    }
    
    // D: Function that deletes a position based on title
    public void removePosition(Integer id) {
    	for (int i = 0; i < positions.size(); i++) {
    		if (positions.get(i).getId().equals(id)) {
    			positions.remove(i);
    		}
    	}
    }
}
