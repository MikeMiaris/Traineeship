package com.example.traineeship.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SupervisorAssignmentFactory {
	private final AssignmentBasedOnLoad assignmentBasedOnLoad;
	private final AssignmentBasedOnInterests assignmentBasedOnInterests;
	
	@Autowired
	public SupervisorAssignmentFactory(AssignmentBasedOnLoad load, AssignmentBasedOnInterests interests) {
		this.assignmentBasedOnLoad = load;
		this.assignmentBasedOnInterests = interests;
	}
	
	public SupervisorAssignmentStrategy create(String strategy) {
		return switch (strategy.toLowerCase()) {
        case "load" -> assignmentBasedOnLoad;
        case "interests" -> assignmentBasedOnInterests;
        default -> throw new IllegalArgumentException("Invalid strategy: " + strategy);
    };
	}
}
