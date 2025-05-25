package com.example.traineeship.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyMapper companyMapper;
	private final TraineeshipPositionMapper traineeshipPositionMapper;
	
	@Autowired
	public CompanyServiceImpl(CompanyMapper companyMapper, TraineeshipPositionMapper traineeshipPositionMapper) {
		this.companyMapper = companyMapper;
		this.traineeshipPositionMapper = traineeshipPositionMapper;
	}
	
	@Override
    public void saveProfile(Company company) {
        companyMapper.save(company);
    }

	@Override
	public Company retrieveProfile(String username) {
		// D: return to mapper to fix findById
		return companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
	}
	
	@Override
	public List<TraineeshipPosition> retrieveAvailablePositions(String username) {
	    Company company = companyMapper.findById(username)
	            .orElseThrow(() ->
	                new IllegalArgumentException("Company not found: " + username));

	    // Get today's date
	    LocalDate today = LocalDate.now();

	    return company.getPositions().stream()
	            .filter(position -> !position.isAssigned() && 
	                                (position.getToDate() == null || !position.getToDate().isBefore(today)))
	            .collect(Collectors.toList());
	}
	
	
	@Override
	public void addPosition(String username, TraineeshipPosition position) {
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		// also add company as field of position
		position.setCompany(company);
		company.announcePosition(position);
		// D: after the above, need to update mapper
		saveProfile(company);
	}

	@Override
	public List<TraineeshipPosition> retrieveAssignedPositions(String username) {
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		
		// Get today's date
	    LocalDate today = LocalDate.now();
		
		return company.getPositions().stream()
				.filter(position -> position.isAssigned() && 
                        (position.getToDate() == null || !position.getToDate().isBefore(today)))
				.collect(Collectors.toList());
	}
	
	
	// D: use to authorize assigned position?
	// maybe access it with mapper and check if it exists??
	@Override
	public void evaluateAssignedPosition(String username, Integer positionId) {
		// get assigned positions
		List<TraineeshipPosition> positions = retrieveAssignedPositions(username);
		
		// find out whether this company manages position
		TraineeshipPosition position =  traineeshipPositionMapper.findById(positionId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Position doesn't exist: " + positionId));
		
		boolean isOK = positions.contains(position);
		
		if (!isOK){
			throw new IllegalArgumentException("Position " + positionId + " is not assigned to your company.");
		}
	}
	
	// D: save an Evaluation based on positionId
	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {
		TraineeshipPosition position =  traineeshipPositionMapper.findById(positionId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Position doesn't exist: " + positionId));
		
		position.addEvaluation(evaluation);
		
	}
	
	// newly added function to delete some assigned position
	@Override
	public void deleteAssignedPosition(String username, Integer positionId) {
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		
		company.removePosition(positionId);
		
		TraineeshipPosition position =  traineeshipPositionMapper.findById(positionId)
                .orElseThrow(() ->
                    new IllegalArgumentException("Position doesn't exist: " + positionId));
		
		traineeshipPositionMapper.delete(position);
		
		companyMapper.save(company);
	}
	
	// another added function to retrieve expired positions and wipe them
	@Override
	public List<TraineeshipPosition> retrieveExpiredPositions(String username) {
		Company company = companyMapper.findById(username)
	            .orElseThrow(() ->
	                new IllegalArgumentException("Company not found: " + username));

	    // Get today's date
	    LocalDate today = LocalDate.now();

	    return company.getPositions().stream()
	            .filter(position -> (position.getToDate() == null || position.getToDate().isBefore(today)))
	            .collect(Collectors.toList());
	}
}
