package com.example.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.services.StudentService;


@Controller
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/student/dashboard")
	public String getStudentDashboard() {
		return "student/dashboard";
	}
	
	@RequestMapping("/student/profile")
	public String retrieveProfile(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		String username = authentication.getName();
		try {
			Student student = studentService.retrieveProfile(username);
			model.addAttribute("student", student);
		}catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/student/dashboard";
		}
		
        return "student/student-profile";
	}
	
	@RequestMapping("/student/save_profile")
	public String saveProfile(@ModelAttribute("student") Student student, Model model) {
		studentService.saveProfile(student);
		return "redirect:/student/dashboard";
	}
	
	@RequestMapping("/student/fill_logbook")
	public String fillLogbook(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();	
		String username = authentication.getName();
		try {
			Student student = studentService.retrieveProfile(username);
			model.addAttribute("position", student.getAssignedTraineeship());
			return "/student/logbook-form";
		}catch(Exception e){
			model.addAttribute("error", e.getMessage());
			return "redirect:/student/dashboard";
		}
	}
	
	@RequestMapping("/student/save_logbook")
	public String saveLogbook(@ModelAttribute("position") TraineeshipPosition position, Model model) {
		try {
			studentService.saveLogbook(position);
			return "redirect:/student/dashboard";
		}catch(Exception e) {
			model.addAttribute("error", e.getMessage());
			return "redirect:/student/dashboard";
		}
	}
}
