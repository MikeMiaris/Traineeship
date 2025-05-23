package com.example.traineeship.controllers;

import com.example.traineeship.services.CommitteeService;
import org.springframework.ui.Model;

public class CommitteController {
	CommitteeService committeeservice;
	
	String getCommitteeDashboard() {
		return "sdsd";
	}
	
	String listTraineeshipApplications(Model model) {
		return "sdsd";
	}
	
	String findPositions(String studentUsername, String strategy, Model model) {
		return "sdsd";
	}
	
	String assignPosition(Integer positionId, String studentUsername, Model model) {
		return "sdsdds";
	}
	
	String assignSupervisor(Integer positionId, String strategy, Model model) {
		return "sadasa";
	}
	
	String listAssignedTraineeships(Model model) {
		return "sasasa";
	}
	
	String completeAssignedTraineeships(Integer positionId, Model model) {
		return "sdsd";
	}
	
	
}
