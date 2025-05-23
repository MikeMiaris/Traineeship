package com.example.traineeship.factories;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        
        Set<String> studentInterestSet = Arrays.stream(student.getInterests().split(",")).map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
        
        
        return positions.stream()
                .filter(position -> {
                    Set<String> positionTopicSet = Arrays.stream(position.getTopics().split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toSet());

                    Set<String> intersection = new HashSet<>(positionTopicSet);
                    intersection.retainAll(studentInterestSet);

                    Set<String> union = new HashSet<>(positionTopicSet);
                    union.addAll(studentInterestSet);

                    return union.isEmpty() ? false : 
                        (double) intersection.size() / union.size() >= 0.5;
                })
                .collect(Collectors.toList());
	}
}
