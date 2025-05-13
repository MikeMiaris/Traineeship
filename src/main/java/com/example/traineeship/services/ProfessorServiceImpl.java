package com.example.traineeship.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.EvaluationMapper;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

public class ProfessorServiceImpl implements ProfessorService{

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private TraineeshipPositionMapper positionMapper;

    @Autowired
    private EvaluationMapper evaluationMapper;

	@Override
	public Professor retrieveProfile(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProfile(Professor professor) {
		professorMapper.save(professor);
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions() {
        List<Professor> allProfessors = professorMapper.findAll();
        List<TraineeshipPosition> result = new ArrayList<>();

        for (Professor prof : allProfessors) {
            result.addAll(prof.getSupervisedPositions());
        }
        return result;
	}

	@Override
	public void evaluateAssignedPosition(Integer position) {
		
	}

	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {

    }
	
}
