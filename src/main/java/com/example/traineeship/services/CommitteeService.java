package com.example.traineeship.services;

import java.util.List;

import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.domainmodel.Student;

public interface CommitteeService {
	List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String Strategy);
	List<Student> retrieveTraineeShipApplications();
	void assignPosition(Integer positionid, String studentUsername);
	void assignSupervisor(Integer positionid, String strategy);
	List<TraineeshipPosition> listAssignedTraineeships();
	void completeAssignedTraineeships(Integer positionid);
}
