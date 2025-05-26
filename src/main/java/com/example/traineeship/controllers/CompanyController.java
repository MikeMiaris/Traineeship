package com.example.traineeship.controllers;

import java.util.List;

// D: imports taken directly from diplomas example
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.TraineeshipPosition;

import com.example.traineeship.services.CompanyService;
import com.example.traineeship.services.UserService;

@Controller
public class CompanyController {
	
	// D: again, a lot of those taken directly from example
	// we'll have to see what's needed
	
	//  try catch exceptions here when calling service functions
	// for example, evaluate assigned just either:
	// - brings you to eval form if position exists and is assigned to company
	// - catches exception and pulls you back with a warning
	
	// I'm changing evaluateAssignedTraineeship, it just makes sense to check the correlation only in service
	// so pass username as argument for the love of god
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyService companyService;
	
	@RequestMapping("/company/dashboard")
	public String getCompanyDashboard() {
		return "company/dashboard";
	}
	
	@RequestMapping("/company/profile")
	public String retrieveProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		String compUsername = authentication.getName();
		try {
			Company company = companyService.retrieveProfile(compUsername);
			model.addAttribute("company", company);
		}catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/dashboard";
		}
		
        return "company/company-profile";
	}
	
	@RequestMapping("/company/save-profile")
	public String saveProfile(@ModelAttribute("company") Company company, Model model) {
		companyService.saveProfile(company);
		return "redirect:/login";
	}
	
	@RequestMapping("/company/list_available_positions")
	public String listAvailablePositions(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		
		try {
			List<TraineeshipPosition> positions = companyService.retrieveAvailablePositions(username);
			model.addAttribute("available_positions", positions);
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/dashboard";
		}
		
		
		return "company/available_positions";
		
	}
	
	@RequestMapping("/company/show_position_form")
	public String showPositionForm(Model model) {
		
		TraineeshipPosition position = new TraineeshipPosition();
		model.addAttribute("position", position);		
		
		return "company/position-form";
	}
	
	@RequestMapping("/company/save_position")
	public String savePosition(@ModelAttribute("position") TraineeshipPosition position, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			companyService.addPosition(username, position);
			return "redirect:/company/list_available_positions";
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/list_available_positions";
		}
		
	}
	
	@RequestMapping("/company/list_assigned_positions")
	public String listAssignedPositions(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			List <TraineeshipPosition> positions = companyService.retrieveAssignedPositions(username);
			model.addAttribute("positions", positions);
			return "company/assigned_positions";
		}catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/dashboard";
		}
		
	}
	
	// D: added function to show expired positions
	@RequestMapping("/company/list_expired_positions")
	public String listExpiredPositions(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			List <TraineeshipPosition> positions = companyService.retrieveExpiredPositions(username);
			model.addAttribute("positions", positions);
			return "company/expired_positions";
		}catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/dashboard";
		}
	}
	
	// D:  this will probably set you on the path of evaluating traineeship
	// authentications in service doesn't make sense to me, so maybe by passing position_id
	// we can access that mapper in company service
	@RequestMapping("/company/evaluate_assigned_traineeship/{position_id}")
	public String evaluateAssignedTraineeship(@PathVariable("position_id") Integer positionId, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		
		// D: check if company can evaluate position, stop otherwise?
		try {
			companyService.evaluateAssignedPosition(username, positionId);
			model.addAttribute("evaluation", new Evaluation());
			return "company/evaluation-form";
		}
		// the way errors are to be handled ig, although dunno if this is somehow even viable
		catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/list_assigned_positions";
		}
	}
	
	// D: now, with an evaluation all set, get to saving
	@RequestMapping("/company/evaluate/{position_id}")
	public String saveEvaluation(@PathVariable("position_id") Integer positionId,
								@ModelAttribute("evaluation") Evaluation evaluation, Model model) {
		try {
			companyService.saveEvaluation(positionId, evaluation);
			return "redirect:/company/list_assigned_positions";
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/list_assigned_positions";
		}
		
	}
	
	// get to remake deletion function
	@RequestMapping("/company/delete_position/{position_id}")
	public String deletePosition(@PathVariable("position_id") Integer positionId, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		try {
			companyService.deleteAssignedPosition(username, positionId);
			return "redirect:/company/list_assigned_positions";
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/company/list_assigned_positions";
		}
	}
	
	// added to accommodate new company creation
	@RequestMapping("/company/new-company-form")
    public String showCompanyForm(@ModelAttribute("company") Company company, Model model) {
        return "company/new-company-form";
    }
}
