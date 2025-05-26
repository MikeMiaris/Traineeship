package com.example.traineeship.controllers;

import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired 
    private ProfessorService professorService;
    
    @GetMapping("/dashboard")
    public String getProfessorDashboard() {
    	return "professor/dashboard";
    }
    
    
    @GetMapping("/profile")
    public String retrieveProfile(Model model) {
        
    	String me = SecurityContextHolder.getContext().getAuthentication().getName();
        
        try {
        	Professor prof = professorService.retrieveProfile(me);
        	model.addAttribute("professor", prof);
        }catch(Exception e){
        	model.addAttribute("error", e.getMessage());
        	return "redirect:/professor/dashboard";

        }
        return "professor/profile-view";
    }

    @PostMapping("/save-profile")
    public String saveProfile(@ModelAttribute("professor") Professor prof, Model model) {
    	professorService.saveProfile(prof);
        return "redirect:/login";
    }


    // GET /professor/traineeships — US14
    @GetMapping("/traineeships")
    public String listAssignedTraineeships(Model model) {
    	try {
    		String me = SecurityContextHolder.getContext().getAuthentication().getName();
    		List<TraineeshipPosition> list = professorService.retrieveAssignedPositions(me);
    		model.addAttribute("positions", list);
    	}catch(Exception e) {
    		model.addAttribute("error", e.getMessage());
    		return "redirect:/professor/dashboard";
    	}
        
        return "professor/traineeships";
    }

    @GetMapping("/evaluate/{positionId}")
    public String evaluateAssignedtraineeship(@PathVariable("positionId") Integer positionId, Model model) {
		
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
			professorService.evaluateAssignedPosition(positionId, username);
			model.addAttribute("evaluation", new Evaluation());
			return "professor/evaluation-form";
		}
		catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/professor/traineeships";
		}
    
    }

    @PostMapping("/evaluate/{positionId}")
    public String saveEvaluation(@PathVariable("positionId") Integer positionId, @ModelAttribute("evaluation") Evaluation evaluation, Model model) {
		
    	try {
			professorService.saveEvaluation(positionId, evaluation);
			return "redirect:/professor/traineeships";
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/professor/traineeships";
		}
    }
    
    @GetMapping("/new-professor-form")
    public String showProfessorForm(@ModelAttribute("professor") Professor prof, Model model) {
        return "professor/new-professor-form";
    }
}
