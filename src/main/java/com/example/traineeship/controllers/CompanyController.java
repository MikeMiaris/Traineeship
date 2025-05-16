package com.example.traineeship.controllers;

import java.util.List;

// D: imports taken directly from diplomas example
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		System.err.println("XXXXXX " + compUsername);
        Company company = companyService.retrieveProfile(compUsername);
        
        model.addAttribute("company", company);
        
        return "company/company-profile";
	}
	
	@RequestMapping("/company/save_profile")
	public String saveProfile(@ModelAttribute("company") Company company, Model model) {
		companyService.saveProfile(company);
		
		return "company/dashboard";
	}
	
	@RequestMapping("/company/list_available_positions")
	public String listAvailablePositions(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String username = authentication.getName();
		System.err.println("Username " + username);
		
		List<TraineeshipPosition> positions = companyService.retrieveAvailablePositions(username);
		
		model.addAttribute("available_positions", positions);
		
		return "company/available_positions";
		
	}
	// D: go from showpositionform onwards here
}
