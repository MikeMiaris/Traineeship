package com.example.traineeship.services;

import java.util.List;

import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;

public interface ProfessorService {

	Professor retreiveProfile(String username);
	void saveProfile(Professor professor);
	List<TraineeshipPosition> retrieveAssignedPositions();
	void evaluateAssignedPosition(Integer position);
	void saveEvaluation(Integer positionId,Evaluation evaluation);
	
}
