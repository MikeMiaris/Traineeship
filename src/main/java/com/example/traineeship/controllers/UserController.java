package com.example.traineeship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.traineeship.domainmodel.User;
import com.example.traineeship.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Home page (also handles login error flag)
    @RequestMapping("/")
    public String getRoot(Model model, @RequestParam(name = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Login failed. Please check your username and password.");
        }
        return "user/homepage"; // templates/user/homepage.html
    }

    // Signup form
    @RequestMapping("/user/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new User());
        return "user/signup"; // templates/user/signup.html
    }

    // Register new user
    @RequestMapping("/user/save-user")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        if (userService.isUserPresent(user)) {
            model.addAttribute("successMessage", "User already registered!");
            return "redirect:/?error=true";
        }

        userService.saveUser(user);
        return "redirect:/login";
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
