package com.example.traineeship.domainmodel;

import java.util.List;

import jakarta.persistence.*;


@Entity 
@Table(name="companies")
public class Company {
	
	@Id
	@Column(name = "company_id")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "companyName")
	private String companyName;
	
	@Column(name = "companyLocation")
	private String companyLocation;
	
	@OneToMany(mappedBy="company")
	private List<TraineeshipPosition> positions ; //Initialize??
	
	
	//contructors
	public Company() {
		super();
	}
	
	public Company(String username, String companyName, String companyLocation) {
		super();
		this.username = username;
		this.companyName = companyName;
		this.companyLocation = companyLocation;
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
	
	
}
