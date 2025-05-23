package com.example.traineeship.factories;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import com.example.traineeship.mappers.ProfessorMapper;



import com.example.traineeship.domainmodel.TraineeshipPosition;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.traineeship.domainmodel.Professor;

public class AssignmentBasedOnInterests implements SupervisorAssignmentStrategy{
	TraineeshipPositionMapper positionsMapper;
	ProfessorMapper professorMapper;
	
	public void assign(Integer positionid) {
	    TraineeshipPosition position = positionsMapper.findById(positionid)
	            .orElseThrow(() -> new IllegalArgumentException("Position not found:" + positionid));
	    
	    List<Professor> professors = professorMapper.findByInterest(position.getTopics());
	    
	    Set<String> postopicSet = Arrays.stream(position.getTopics().split(","))
	            .map(String::trim)
	            .filter(s -> !s.isEmpty())
	            .collect(Collectors.toSet());
	    
	    // Calculate Jaccard similarity for each professor and find the one with highest score
	    Optional<Professor> bestProfessor = professors.stream()
	            .map(prof -> {
	                // Split professor's interests into a set
	                Set<String> profInterests = Arrays.stream(prof.getInterests().split(","))
	                        .map(String::trim)
	                        .filter(s -> !s.isEmpty())
	                        .collect(Collectors.toSet());
	                
	                // Calculate Jaccard similarity
	                double similarity = calculateJaccardSimilarity(postopicSet, profInterests);
	                return new AbstractMap.SimpleEntry<>(prof, similarity);
	            })
	            .max(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
	            .map(AbstractMap.SimpleEntry::getKey);
	    
	    if (bestProfessor.isPresent()) {
	        // Assign the best professor to the position
	    	position.setSupervisor(bestProfessor.get());
			position.setAssigned(true);
			bestProfessor.get().addSupervisedPosition(position);
	    } else {
	        throw new IllegalStateException("No suitable professor found for position: " + positionid);
	    }
	}

	private double calculateJaccardSimilarity(Set<String> set1, Set<String> set2) {
	    if (set1.isEmpty() && set2.isEmpty()) {
	        return 0.0;
	    }
	    
	    Set<String> intersection = new HashSet<>(set1);
	    intersection.retainAll(set2);
	    
	    Set<String> union = new HashSet<>(set1);
	    union.addAll(set2);
	    
	    return (double) intersection.size() / union.size();
	}
	
}
