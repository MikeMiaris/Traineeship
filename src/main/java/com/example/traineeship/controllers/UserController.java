package com.example.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.traineeship.domainmodel.User;
import com.example.traineeship.services.UserServiceImpl;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    
    // signin form
    @RequestMapping("/login")
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
        
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            userDetails, 
            userDetails.getPassword(),
            userDetails.getAuthorities()
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
}
