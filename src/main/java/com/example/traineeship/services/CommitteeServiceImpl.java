package com.example.traineeship.services;

import java.util.List;
import com.example.traineeship.factories.PositionSearchFactory;
import com.example.traineeship.factories.PositionSearchStrategy;
import com.example.traineeship.factories.SupervisorAssignmentFactory;
import com.example.traineeship.factories.SupervisorAssignmentStrategy;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;

public class CommitteeServiceImpl {
	PositionSearchFactory positionsSearchFactory;
	SupervisorAssignmentFactory supervisorAssignmentFactory;
	StudentMapper studentMapper;
	TraineeshipPositionMapper positionMapper;
	
	
	List<TraineeshipPosition> retrievePositionsForApplicant(String applicantUsername, String Strategy){
		PositionSearchStrategy searchtype = positionsSearchFactory.create(Strategy);
		return searchtype.search(applicantUsername);
		
		
		
	}
	List<Student> retrieveTraineeShipApplications(){
		return studentMapper.LookingForTrain();
		
		
	}
	void assignPosition(Integer positionid, String studentUsername){
		TraineeshipPosition position = positionMapper.findById(positionid).orElseThrow(()-> new IllegalArgumentException("No such position found: " + positionid));
		Student student = studentMapper.findById(studentUsername).orElseThrow(()-> new IllegalArgumentException("No such student found: " + studentUsername));
		
		position.setAssigned(true);
		position.setStudent(student);
		student.setAssignedTraineeship(position);
		
		
	}
	void assignSupervisor(Integer positionid, String strategy){
		TraineeshipPosition position = positionMapper.findById(positionid).orElseThrow(()-> new IllegalArgumentException("No such position found: " + positionid));
		SupervisorAssignmentStrategy assigntype = supervisorAssignmentFactory.create(strategy);
		assigntype.assign(positionid);
		
		
	}
	List<TraineeshipPosition> listAssignedTraineeships(){
		return positionMapper.getallAssigned();
		
	}
	void completeAssignedTraineeships(Integer positionid) {
		TraineeshipPosition position = positionMapper.findById(positionid).orElseThrow(()-> new IllegalArgumentException("No such position found: " + positionid));
		List<Evaluation> evaluations = position.getEvaluations();
		int avg = 0;
		int total = 0;
		for(int i = 0; i < evaluations.size(); i++) {
			total += evaluations.get(i).getMotivation();
			total += evaluations.get(i).getEffectiveness();
			total += evaluations.get(i).getEfficiency();
			
			total = total/3;
			
			avg = (avg + total)/2; 
		}
		
		boolean pass = false;
		
		if(avg >= 3) {
			pass = true;
		}
		
		position.setPassFailGrade(pass);
		
		
	} 
}
