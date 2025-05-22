package com.example.traineeship.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

@Service
public class ProfessorServiceImpl implements ProfessorService{

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private TraineeshipPositionMapper positionMapper;


    
    @Autowired
    public ProfessorServiceImpl(ProfessorMapper professorMapper, TraineeshipPositionMapper positionMapper ) {
    	this.professorMapper = professorMapper;
    	this.positionMapper = positionMapper;
    }
    
    
	@Override
	public Professor retrieveProfile(String username) {
		return professorMapper.findById(username).orElseThrow(() ->
        new IllegalArgumentException("Company not found: " + username));
	}

	@Override
	public void saveProfile(Professor professor) {
		professorMapper.save(professor);
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions(String username) {
        
        Professor prof = professorMapper.findById(username).orElseThrow(() ->
        	new IllegalArgumentException("Professor not found: " + username));;
        
        return prof.getSupervisedPositions();
        

	}

	@Override
	public void evaluateAssignedPosition(Integer positionId, String username) {
        
		TraineeshipPosition pos = positionMapper.findById(positionId).orElseThrow(() -> new IllegalArgumentException("Position not found"));
        
        Professor prof = professorMapper.findById(username).orElseThrow(() ->
        	new IllegalArgumentException("Professor not found: " + username));
            
        if (!pos.getSupervisor().getUsername().equals(prof.getUsername())) {
        	throw new SecurityException("Not authorized to evaluate this position");
        }
	}

	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {

		TraineeshipPosition pos = positionMapper.findById(positionId).orElseThrow(() -> new IllegalArgumentException("Position not found"));
		
		pos.addEvaluation(evaluation);
		
    }
	
}
