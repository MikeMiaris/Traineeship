package com.example.traineeship.factories;

import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import com.example.traineeship.domainmodel.Professor;

public class AssignmentBasedOnLoad implements SupervisorAssignmentStrategy {
	TraineeshipPositionMapper positionsMapper;
	ProfessorMapper professorMapper;
	
	public void assign(Integer positionid) {
		TraineeshipPosition position = positionsMapper.findById(positionid).orElseThrow(()-> new IllegalArgumentException("Position not found:" + positionid));
		Professor professor = professorMapper.findByLoad(null); ////CHECK CHECK
		
		position.setSupervisor(professor);
		position.setAssigned(true);
		professor.addSupervisedPosition(position);
		
	}
}
