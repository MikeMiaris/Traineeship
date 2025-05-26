package com.example.traineeship.controllers;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.services.CommitteeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommitteController {
	
	@Autowired
	CommitteeService committeeService;
	
	@RequestMapping("/Committee/dashboard")
	String getCommitteeDashboard() {
		return "Committee/dashboard";
	}
	
	@RequestMapping("/Committee/List_Traineeship_Applications")
	String listTraineeshipApplications(Model model) {
		try {
			List<Student> applicants = committeeService.retrieveTraineeShipApplications();
			model.addAttribute("Traineeship_Applications", applicants);
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/Committee/dashboard";
		}
		
		
		return "Committee/Traineeship_Applications";
	}
	
	
	@RequestMapping("/Committee/find_positions/{studentUsername}/{strategy}")
	String findPositions(@PathVariable("studentUsername") String studentUsername, @PathVariable("strategy") String strategy, Model model) {
		try {
			List<TraineeshipPosition> positions = committeeService.retrievePositionsForApplicant(studentUsername, strategy);
			model.addAttribute("studentUsername",studentUsername);
			model.addAttribute("Traineeship_Positions",positions);
		}
		catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/Committee/dashboard";
		}
		return "/Committee/Traineeship_Positions";
	}
	
	@RequestMapping("/Committee/assign_Position/{studentUsername}/{position_id}/{strategy}")
	String assignPosition(@PathVariable("position_id") Integer positionId, @PathVariable("strategy") String strategy, @PathVariable("studentUsername") String studentUsername, Model model) {
		try {
			committeeService.assignPosition(positionId, studentUsername);
			return "/Committee/assign_supervisor/"+positionId+"/"+strategy;
		}
		catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/Committee/dashboard";
		}
	}
	
	@RequestMapping("/Committee/assign_supervisor/{position_id}/{strategy}")
	String assignSupervisor(@PathVariable("position_id") Integer positionId, @PathVariable("strategy") String strategy, Model model) {
		try {
			committeeService.assignSupervisor(positionId, strategy);
			return "redirect:/Committee/list_Assigned_Traineeships";
		}
		catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/Committee/dashboard";
		}
	}
	
	@RequestMapping("/Committee/list_Assigned_Traineeships")
	String listAssignedTraineeships(Model model) {
		try {
			List<TraineeshipPosition> positions = committeeService.listAssignedTraineeships();
			model.addAttribute("/Committee/Assigned_Traineeships",positions);
		}
		catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/Committee/dashboard";
		}
		return "/Committee/Assigned_Traineeships";
	}
	
	
	@RequestMapping("/Committee/complete_assigned_traineeships")
	String completeAssignedTraineeships(@PathVariable("position_id") Integer positionId, Model model) {
		try {
			committeeService.completeAssignedTraineeships(positionId);
			return "redirect:/Committtee/Assigned_Traineeships";
		}
		catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/Committee/Assigned_Traineeships";
		}
	}
	
	
}
