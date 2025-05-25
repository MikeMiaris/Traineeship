package com.example.traineeship.domainmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TraineeshipPositionModelTest {
    
	private TraineeshipPosition position;

    @BeforeEach
    void setUp(){
        position = new TraineeshipPosition("Backend Intern","Develop REST APIs",LocalDate.of(2025, 6, 1), LocalDate.of(2025, 9, 1),"Spring Boot, APIs","Java, SQL");
        position.setEvaluations(new ArrayList<>());
    }

    @Test
    @DisplayName("Constructor initializes all fields correctly and sets defaults")
    void testConstructorAndDefaults(){
        assertEquals("Backend Intern", position.getTitle());
        assertEquals("Develop REST APIs", position.getDescription());
        assertEquals(LocalDate.of(2025, 6, 1), position.getFromDate());
        assertEquals(LocalDate.of(2025, 9, 1), position.getToDate());
        assertEquals("Spring Boot, APIs", position.getTopics());
        assertEquals("Java, SQL", position.getSkills());
        assertFalse(position.isAssigned());
        assertFalse(position.isPassFailGrade());
        assertEquals("", position.getStudentLogbook());
    }

    @Test
    @DisplayName("Setters and getters work correctly")
    void testSettersAndGetters(){
        Student student = new Student();
        Professor prof = new Professor();
        Company company = new Company();
        
        position.setAssigned(true);
        position.setPassFailGrade(true);
        position.setStudentLogbook("My daily notes");
        position.setStudent(student);
        position.setSupervisor(prof);
        position.setCompany(company);

        assertTrue(position.isAssigned());
        assertTrue(position.isPassFailGrade());
        assertEquals("My daily notes", position.getStudentLogbook());
        assertEquals(student, position.getStudent());
        assertEquals(prof, position.getSupervisor());
        assertEquals(company, position.getCompany());
    }

    @Test
    @DisplayName("Evaluation can be added to the list")
    void testAddEvaluation(){
        Evaluation evaluation = new Evaluation();
        position.addEvaluation(evaluation);
        assertEquals(1, position.getEvaluations().size());
        assertTrue(position.getEvaluations().contains(evaluation));
    }

    @Test
    @DisplayName("ID can be set and retrieved")
    void testId(){
        position.setId(42);
        assertEquals(42, position.getId());
    }
}
