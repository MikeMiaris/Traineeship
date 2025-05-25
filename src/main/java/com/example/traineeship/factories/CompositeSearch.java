package com.example.traineeship.factories;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

@Service
public class CompositeSearch implements PositionSearchStrategy{
	CompanyMapper companyMapper;
	StudentMapper studentMapper;
	TraineeshipPositionMapper PositionMapper;
	SearchBasedOnInterests interests;
	
	public CompositeSearch(CompanyMapper companymapper, StudentMapper studentmapper, TraineeshipPositionMapper positionmapper, SearchBasedOnInterests interests) {
		this.companyMapper = companymapper;
		this.studentMapper = studentmapper;
		this.PositionMapper = positionmapper;
		this.interests = new SearchBasedOnInterests(studentmapper,positionmapper);
	}
	
	public List<TraineeshipPosition> search(String applicantUsername){
		Student student = studentMapper.findById(applicantUsername).orElseThrow(()-> new IllegalArgumentException("No such applicant"));;
		if (student == null || student.getPreferredLocation() == null) {
            return Collections.emptyList();
        }


        List<TraineeshipPosition> positionsloc = PositionMapper.findByLocation(
            student.getPreferredLocation() 
        );
        
        List<TraineeshipPosition> positionsInt = interests.search(
                student.getInterests() 
            );
        
        List<TraineeshipPosition> positions = positionsloc.stream().filter(positionsInt::contains).collect(Collectors.toList());
        
        return positions;
	}
}