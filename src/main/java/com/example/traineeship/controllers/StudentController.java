package com.example.traineeship.controllers;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/dashboard")
    public String getStudentDashboard() {
        return "student/dashboard";
    }

    @GetMapping("/profile")
    public String retrieveProfile(Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Student student = studentService.retrieveProfile(username);
            model.addAttribute("student", student);
            return "student/profile-view";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/dashboard";
        }
    }

    @PostMapping("/save_profile")
    public String saveProfile(@ModelAttribute("student") Student student, Model model) {
        try {
            studentService.saveProfile(student);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/profile";
        }
    }

    @GetMapping("/fill_logbook")
    public String fillLogbook(Model model, RedirectAttributes flash) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = studentService.retrieveProfile(username);
        
        
        TraineeshipPosition position = student.getAssignedTraineeship();
        
        if (position == null) {
            flash.addFlashAttribute("error", "You have not been assigned a traineeship yet.");
            return "redirect:/student/dashboard";
        }
        
        // Check if the logbook field is null
        if (position.getStudentLogbook() == null) {
            position.setStudentLogbook("");  // Initialize with empty string for new entries
        }
        
        model.addAttribute("position", position);
        return "student/logbook-form";

    }

    @PostMapping("/save_logbook")
    public String saveLogbook(@ModelAttribute("position") TraineeshipPosition position, Model model) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Student student = studentService.retrieveProfile(username);
            
            // Get the existing position to ensure all fields are preserved
            TraineeshipPosition existingPosition = student.getAssignedTraineeship();
            if (existingPosition == null) {
                throw new Exception("No assigned traineeship position");
            }
            
            // Update only the logbook field
            existingPosition.setStudentLogbook(position.getStudentLogbook());
            
            studentService.saveLogbook(existingPosition);
            return "redirect:/student/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/fill_logbook";
        }
    }

    @GetMapping("/new-student-form")
    public String newStudentForm(@ModelAttribute("student") Student stud,Model model) {
        return "student/new-student-form";
    }

}
