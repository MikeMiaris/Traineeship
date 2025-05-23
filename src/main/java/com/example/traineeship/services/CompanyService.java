package com.example.traineeship.services;

import java.util.List;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.TraineeshipPosition;

public interface CompanyService {

	Company retrieveProfile(String username);
	void saveProfile(Company company);
	List<TraineeshipPosition> retrieveAvailablePositions(String username);
	void addPosition(String username, TraineeshipPosition position);
	List<TraineeshipPosition> retrieveAssignedPositions(String username);
	// added to delete positions
	void deleteAssignedPosition(String username, Integer positionId);
	public List<TraineeshipPosition> retrieveExpiredPositions(String username);
	
	void evaluateAssignedPosition(String username, Integer positionId);
	void saveEvaluation(Integer positionId, Evaluation evaluation);
}
