package com.example.traineeship.domainmodel;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfessorModelTest {

    @Test
    public void testCreateProfessor(){
        Professor professor = new Professor("john123", "Dr. John Doe", "AI, ML");

        assertEquals("john123", professor.getUsername());
        assertEquals("Dr. John Doe", professor.getProfessorName());
        assertEquals("AI, ML", professor.getInterests());
        assertNotNull(professor.getSupervisedPositions());
        assertTrue(professor.getSupervisedPositions().isEmpty());
    }

    @Test
    public void testAddSupervisedPosition(){
        Professor professor = new Professor("jane123", "Dr. Jane Smith", "Data Science");
        TraineeshipPosition position = new TraineeshipPosition();
        professor.addSupervisedPosition(position);

        assertEquals(1, professor.getSupervisedPositions().size());
        assertSame(position, professor.getSupervisedPositions().get(0));
    }
}
