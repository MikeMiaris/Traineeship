package com.example.traineeship.factories;

import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.traineeship.domainmodel.Professor;

@Service
public class AssignmentBasedOnLoad implements SupervisorAssignmentStrategy {
	private final TraineeshipPositionMapper positionsMapper;
	private final ProfessorMapper professorMapper;
	
	@Autowired
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
