package com.example.traineeship.factories;
import java.util.Collections;
import java.util.List;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;


public class SearchBasedOnLocation implements PositionSearchStrategy{
	CompanyMapper companyMapper;
	StudentMapper studentMapper;
	TraineeshipPositionMapper TraineeshipPositionMapper;
	
	public List<TraineeshipPosition> search(String applicantUsername){
		Student student = studentMapper.findByUsername(applicantUsername);
		if (student == null || student.getPreferredLocation() == null) {
            return Collections.emptyList();
        }


        List<TraineeshipPosition> positions = com.example.traineeship.mappers.TraineeshipPositionMapper.findByLocation(
            student.getPreferredLocation() 
        );
        
        return positions;
	}
}
