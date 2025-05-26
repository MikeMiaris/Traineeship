package com.example.traineeship.factories;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

@Service
public class SearchBasedOnLocation implements PositionSearchStrategy{
	

	private  final StudentMapper studentMapper;
	private final TraineeshipPositionMapper positionMapper;
	
	 @Autowired
	    public SearchBasedOnLocation( StudentMapper studentMapper, TraineeshipPositionMapper positionMapper
	    ) { 
		 //this.companyMapper = companyMapper;
	     this.studentMapper = studentMapper;
	     this.positionMapper = positionMapper;
	    }
	
	
	
	public List<TraineeshipPosition> search(String applicantUsername){
		
		Student student = studentMapper.findById(applicantUsername).orElseThrow(()-> new IllegalArgumentException("No such applicant"));;
		
		
        
        return positionMapper.findByLocation(student.getPreferredLocation());
	}
}
