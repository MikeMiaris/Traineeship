package com.example.traineeship.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.EvaluationMapper;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

@Service
public class ProfessorServiceImpl implements ProfessorService{

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private TraineeshipPositionMapper positionMapper;

    @Autowired
    private EvaluationMapper evaluationMapper;

	@Override
	public Professor retrieveProfile(String username) {
		return professorMapper.findByUsername(username);
	}

	@Override
	public void saveProfile(Professor professor) {
		professorMapper.save(professor);
		
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions() {
        
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor prof = professorMapper.findByUsername(username);
        return prof != null ? prof.getSupervisedPositions() : new ArrayList<>();
	}

	@Override
	public void evaluateAssignedPosition(Integer positionId) {
        TraineeshipPosition pos = positionMapper.findById(positionId).orElseThrow(() -> new IllegalArgumentException("Position not found"));
            String me = SecurityContextHolder.getContext().getAuthentication().getName();
            if (pos.getSupervisor() == null || !pos.getSupervisor().getUsername().equals(me)) {
                throw new SecurityException("Not authorized to evaluate this position");
            }
	}

	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {

    }
	
}
