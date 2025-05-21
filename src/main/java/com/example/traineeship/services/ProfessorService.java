package com.example.traineeship.services;

import java.util.List;

import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;

public interface ProfessorService {

	Professor retrieveProfile(String username);
	void saveProfile(Professor professor);
	List<TraineeshipPosition> retrieveAssignedPositions(String username);
	void evaluateAssignedPosition(Integer positionId, String username);
	void saveEvaluation(Integer positionId,Evaluation evaluation);
	
}
