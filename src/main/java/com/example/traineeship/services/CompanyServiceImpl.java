package com.example.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
// D: to find out company without username as argument
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CompanyServiceImpl implements CompanyService {

	private final CompanyMapper companyMapper;
	
	@Autowired
	public CompanyServiceImpl(CompanyMapper companyMapper) {
		this.companyMapper = companyMapper;
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
	
	// D: Function that gets non occupied positions
	@Override
	public List<TraineeshipPosition> retrieveAvailablePositions(String username) {
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		
		// D: throw some exception if positions are not found -- TODO
		
		return company.getPositions().stream()
				.filter(position -> !position.isAssigned())
				.collect(Collectors.toList());
	}
	
	
	@Override
	public void addPosition(String username, TraineeshipPosition position) {
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		company.announcePosition(position);
		// D: after the above, need to update mapper
		saveProfile(company);
	}

	@Override
	public List<TraineeshipPosition> retreiveAssignedPositions(String username) {
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		
		// D: throw some exception if positions are not found -- TODO
		
		return company.getPositions().stream()
				.filter(position -> position.isAssigned())
				.collect(Collectors.toList());
	}
	
	
	// D: unclear use
	@Override
	public void evaluateAssignedPosition(Integer positionId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		
		
	}
	
	// D: save an Evaluation based on positionId
	@Override
	public void saveEvaluation(Integer positionId, Evaluation evaluation) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Company company =  companyMapper.findById(username)
                .orElseThrow(() ->
                    new IllegalArgumentException("Company not found: " + username));
		
		
	}
}
