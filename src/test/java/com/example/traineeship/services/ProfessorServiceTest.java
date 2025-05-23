package com.example.traineeship.services;

import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfessorServiceTest {

    private ProfessorMapper professorMapper;
    private TraineeshipPositionMapper positionMapper;
    private ProfessorServiceImpl professorService;

    @BeforeEach
    void setUp() {
        professorMapper = mock(ProfessorMapper.class);
        positionMapper = mock(TraineeshipPositionMapper.class);
        professorService = new ProfessorServiceImpl(professorMapper, positionMapper);
    }

    @Test
    void testRetrieveProfileReturnsProfessor() {
        Professor prof = new Professor("john", "John Doe", "AI");
        when(professorMapper.findById("john")).thenReturn(Optional.of(prof));
        Professor result = professorService.retrieveProfile("john");
        assertEquals("john", result.getUsername());
        assertEquals("John Doe", result.getProfessorName());
        assertEquals("AI", result.getInterests());
    }

    @Test
    void testRetrieveProfileThrowsIfNotFound() {
        when(professorMapper.findById("john")).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> { professorService.retrieveProfile("john");});
        assertTrue(exception.getMessage().contains("Company not found"));
    }

    @Test
    void testRetrieveAssignedPositionsReturnsList() {
        Professor prof = new Professor("john", "John Doe", "AI");
        List<TraineeshipPosition> positions = new ArrayList<>();
        prof.setSupervisedPositions(positions);
        when(professorMapper.findById("john")).thenReturn(Optional.of(prof));
        List<TraineeshipPosition> result = professorService.retrieveAssignedPositions("john");
        assertSame(positions, result);
    }

    @Test
    void testEvaluateAssignedPositionSuccess() {
        Professor prof = new Professor("john", "John Doe", "AI");
        TraineeshipPosition pos = mock(TraineeshipPosition.class);
        when(pos.getSupervisor()).thenReturn(prof);
        when(positionMapper.findById(1)).thenReturn(Optional.of(pos));
        when(professorMapper.findById("john")).thenReturn(Optional.of(prof));
        assertDoesNotThrow(() -> professorService.evaluateAssignedPosition(1, "john"));
    }

    @Test
    void testEvaluateAssignedPositionThrowsSecurityException() {
        Professor prof = new Professor("john", "John Doe", "AI");
        Professor anotherProf = new Professor("mike", "Mike Smith", "ML");
        TraineeshipPosition pos = mock(TraineeshipPosition.class);
        when(pos.getSupervisor()).thenReturn(anotherProf);
        when(positionMapper.findById(1)).thenReturn(Optional.of(pos));
        when(professorMapper.findById("john")).thenReturn(Optional.of(prof));
        assertThrows(SecurityException.class, () -> professorService.evaluateAssignedPosition(1, "john"));
    }

    @Test
    void testSaveEvaluationAddsEvaluation() {
        TraineeshipPosition pos = mock(TraineeshipPosition.class);
        Evaluation eval = new Evaluation();
        when(positionMapper.findById(1)).thenReturn(Optional.of(pos));
        professorService.saveEvaluation(1, eval);
        verify(pos, times(1)).addEvaluation(eval);
    }
}
