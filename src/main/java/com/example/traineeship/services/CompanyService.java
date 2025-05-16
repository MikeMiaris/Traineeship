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
	List<TraineeshipPosition> retreiveAssignedPositions(String username);
	//void deleteAssignedPosition(Integer positionId); -- DON'T NEED? TODO
	void evaluateAssignedPosition(Integer positionId);
	void saveEvaluation(Integer positionId, Evaluation evaluation);
}
