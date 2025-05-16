package com.example.traineeship.controllers;

import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.services.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProfessorControllerTest {

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void showEvalForm_rendersForm() throws Exception {
        doNothing().when(professorService).evaluateAssignedPosition(10);

        mockMvc.perform(get("/professor/evaluate/10"))
               .andExpect(status().isOk())
               .andExpect(view().name("professor/evaluation-form"))
               .andExpect(model().attributeExists("evaluation"))
               .andExpect(model().attribute("positionId", 10));
    }

    @Test
    void submitEval_redirectsToList() throws Exception {
        doNothing().when(professorService).saveEvaluation(eq(10), any(Evaluation.class));

        mockMvc.perform(post("/professor/evaluate/10")
               .param("motivation", "5")
               .param("efficiency", "4")
               .param("effectiveness", "3"))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/professor/traineeships"));
    }
}
