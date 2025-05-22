package com.example.traineeship.factories;

public abstract class SupervisorAssignmentFactory {
	AssignmentBasedOnLoad assignmentBasedOnLoad;
	AssignmentBasedOnInterests assignmentBasedOnInterests;
	
	public SupervisorAssignmentStrategy create(String strategy) {
		return switch (strategy.toLowerCase()) {
        case "location" -> assignmentBasedOnLoad;
        case "interests" -> assignmentBasedOnInterests;
        default -> throw new IllegalArgumentException("Invalid strategy: " + strategy);
    };
	}
}
