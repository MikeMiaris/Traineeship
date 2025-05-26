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
            return "/student/profile-view";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/dashboard";
        }
    }

    @PostMapping("/save_profile")
    public String saveProfile(@ModelAttribute("student") Student student, Model model) {
        try {
            studentService.saveProfile(student);
            return "redirect:/student/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/profile";
        }
    }

    @GetMapping("/fill_logbook")
    public String fillLogbook(Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();;
        try {
            Student student = studentService.retrieveProfile(username);
            model.addAttribute("position", student.getAssignedTraineeship());
            return "student/logbook-form";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/dashboard";
        }
    }

    @PostMapping("/save_logbook")
    public String saveLogbook(@ModelAttribute("position") TraineeshipPosition position, Model model) {
        try {
            studentService.saveLogbook(position);
            return "redirect:/student/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/student/fill_logbook";
        }
    }

    @GetMapping("/new-student-form")
    public String newStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/new-student-form";
    }

}
