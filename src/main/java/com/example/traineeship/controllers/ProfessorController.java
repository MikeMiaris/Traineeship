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

    @Autowired private ProfessorService professorService;

    // GET /professor/profile — render form for US13
    @GetMapping("/profile")
    public String retrieveProfile(Model model) {
        String me = SecurityContextHolder.getContext().getAuthentication().getName();
        Professor prof = professorService.retrieveProfile(me);
        if (prof == null) prof = new Professor();
        prof.setUsername(me);
        model.addAttribute("profile", prof);
        return "professor/profile-form";
    }

    // POST /professor/profile — handle profile save
    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute("profile") Professor professor) {
        professor.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        professorService.saveProfile(professor);
        return "redirect:/professor/traineeships";
    }

    // GET /professor/traineeships — US14
    @GetMapping("/traineeships")
    public String listAssignedTraineeships(Model model) {
        List<TraineeshipPosition> list = professorService.retrieveAssignedPositions();
        model.addAttribute("positions", list);
        return "professor/traineeships";
    }

    // GET /professor/evaluate/{id} — show evaluation form (US15)
    @GetMapping("/evaluate/{positionId}")
    public String showEvalForm(@PathVariable Integer positionId, Model model) {
        professorService.evaluateAssignedPosition(positionId);
        model.addAttribute("evaluation", new Evaluation());
        model.addAttribute("positionId", positionId);
        return "professor/evaluation-form";
    }

    // POST /professor/evaluate/{id} — handle the submission (US15)
    @PostMapping("/evaluate/{positionId}")
    public String submitEval(@PathVariable Integer positionId,
                             @ModelAttribute Evaluation evaluation) {
        professorService.saveEvaluation(positionId, evaluation);
        return "redirect:/professor/traineeships";
    }
}
