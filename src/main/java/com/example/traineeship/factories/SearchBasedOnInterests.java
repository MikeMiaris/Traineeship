package com.example.traineeship.factories;

import java.util.Collections;
import java.util.List;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

public class SearchBasedOnInterests implements PositionSearchStrategy {
	TraineeshipPositionMapper PositionMapper;
	StudentMapper studentMapper;
	
	public List<TraineeshipPosition> search(String applicantUsername){
		Student student = studentMapper.findById(applicantUsername).orElseThrow(()-> new IllegalArgumentException("No such applicant"));
		if (student == null || student.getInterests() == null) {
            return Collections.emptyList();
        }


        List<TraineeshipPosition> positions = PositionMapper.findBytopic(
            student.getInterests() 
        );
        
        return positions;
	}
}
