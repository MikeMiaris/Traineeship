package com.example.traineeship.factories;

import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import com.example.traineeship.domainmodel.Professor;

public class AssignmentBasedOnLoad implements SupervisorAssignmentStrategy {
	TraineeshipPositionMapper positionsMapper;
	ProfessorMapper professorMapper;
	
	public AssignmentBasedOnLoad(TraineeshipPositionMapper posmapper, ProfessorMapper profmapper) {
		this.positionsMapper = posmapper;
		this.professorMapper = profmapper;
	}
	
	public void assign(Integer positionid) {
		TraineeshipPosition position = positionsMapper.findById(positionid).orElseThrow(()-> new IllegalArgumentException("Position not found:" + positionid));
		Professor professor = professorMapper.findByLoad(); ////CHECK CHECK
		
		position.setSupervisor(professor);
		position.setAssigned(true);
		professor.addSupervisedPosition(position);
		
	}
}
