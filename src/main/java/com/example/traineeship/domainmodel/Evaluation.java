package com.example.traineeship.domainmodel;

import jakarta.persistence.*;

@Entity @Table(name="evaluations")
public class Evaluation {

	
	@Id
	@Column(name = "evaluation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Enumerated(EnumType.STRING)
	private EvaluationType evaluationType;
	
	@Column(name = "motivation")
	private int motivation;
	
	@Column(name = "efficiency")
	private int efficiency;
	
	@Column(name = "effectiveness")
	private int effectiveness;
	
	
	private TraineeshipPosition position;
	
	//Constructors
	public Evaluation() {
		
	}
	
	public Evaluation(EvaluationType evaluationType, int motivation, int efficiency, int effectiveness) {
		super();
		this.evaluationType = evaluationType;
		this.motivation = motivation;
		this.efficiency = efficiency;
		this.effectiveness = effectiveness;
	}

	
    //Getters and Setters
    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public int getMotivation(){
        return motivation;
    }

    public void setMotivation(int motivation){
        this.motivation = motivation;
    }

    public int getEfficiency(){
        return efficiency;
    }

    public void setEfficiency(int efficiency){
        this.efficiency = efficiency;
    }

    public int getEffectiveness(){
        return effectiveness;
    }

    public void setEffectiveness(int effectiveness){
        this.effectiveness = effectiveness;
    }
	
	
}
