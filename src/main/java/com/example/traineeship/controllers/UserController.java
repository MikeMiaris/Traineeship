package com.example.traineeship.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.traineeship.domainmodel.User;
import com.example.traineeship.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    // signin form
    @RequestMapping("/signin")
    public String signinForm(Model model) {
    	return "user/signin";
    }

    // Signup form
    @RequestMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/signup"; // templates/user/signup.html
    }

    @RequestMapping("/user/save-user")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        if (userService.isUserPresent(user)) {
            model.addAttribute("successMessage", "User already registered!");
            return "redirect:/?error=true";
        }
        
        // Save user first
        userService.saveUser(user);
        
        // Auto-login
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Redirect based on role
        switch (user.getRole()) {
            case STUDENT:
                return "redirect:/student/new-student-form";
            case COMPANY:
                return "redirect:/company/new-company-form";
            case PROFESSOR:
                return "redirect:/professor/new-professor-form";
            case COMMITTEE:
                return "redirect:/committee/new-committee-form";
            default:
                return "redirect:/?error=true";
        }
    }
    
    
    
    // Role-based redirection after login
    @RequestMapping("/redirect-by-role")
    public String redirectBasedOnRole(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return "redirect:/?error=true";
        }
        
        
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        switch (role) {
            case "ROLE_STUDENT":
                return "redirect:/student/dashboard";
            case "ROLE_COMPANY":
                return "redirect:/company/dashboard";
            case "ROLE_PROFESSOR":
                return "redirect:/professor/dashboard";
            case "ROLE_COMMITTEE":
                return "redirect:/committee/dashboard";
            default:
                return "redirect:/?error=true";
        }
    }
}
