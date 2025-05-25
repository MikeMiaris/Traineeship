package com.example.traineeship.services;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    private CompanyMapper companyMapper;
    private TraineeshipPositionMapper positionMapper;
    private CompanyServiceImpl companyService;

    @BeforeEach
    void setUp() {
        companyMapper = mock(CompanyMapper.class);
        positionMapper = mock(TraineeshipPositionMapper.class);
        companyService = new CompanyServiceImpl(companyMapper, positionMapper);
    }

    @Test
    @DisplayName("Test saving and retrieving company profile")
    void testSaveAndRetrieveProfile() {
        Company company = new Company("techcorp", "Tech Corp", "Athens");
        when(companyMapper.findById("techcorp")).thenReturn(Optional.of(company));
        companyService.saveProfile(company);
        verify(companyMapper).save(company);
        Company result = companyService.retrieveProfile("techcorp");
        assertEquals("techcorp", result.getUsername());
    }

    @Test
    @DisplayName("Test retrieving available positions (unassigned and not expired)")
    void testRetrieveAvailablePositions() {
        Company company = new Company("alpha", "Alpha Ltd", "Thessaloniki");
        TraineeshipPosition pos1 = new TraineeshipPosition("AI Intern", "Desc", LocalDate.now(), LocalDate.now().plusDays(5), "AI", "Req");
        TraineeshipPosition pos2 = new TraineeshipPosition("ML Intern", "Desc", LocalDate.now(), LocalDate.now().minusDays(1), "ML", "Req");
        
        pos1.setAssigned(false);
        pos2.setAssigned(false);
        company.setPositions(Arrays.asList(pos1, pos2));
        when(companyMapper.findById("alpha")).thenReturn(Optional.of(company));
        List<TraineeshipPosition> result = companyService.retrieveAvailablePositions("alpha");
        assertEquals(1, result.size());
        assertTrue(result.contains(pos1));
    }

    @Test
    @DisplayName("Test adding position to company")
    void testAddPosition() {
        Company company = new Company("beta", "Beta Ltd", "Patras");
        when(companyMapper.findById("beta")).thenReturn(Optional.of(company));
        TraineeshipPosition pos = new TraineeshipPosition("SE Intern", "Details", LocalDate.now(), LocalDate.now().plusDays(10), "Software", "Req");
        companyService.addPosition("beta", pos);
        assertTrue(company.getPositions().contains(pos));
        verify(companyMapper).save(company);
    }

    @Test
    @DisplayName("Test retrieving assigned and non-expired positions")
    void testRetrieveAssignedPositions() {
        Company company = new Company("gamma", "Gamma Ltd", "Crete");
        TraineeshipPosition assigned = new TraineeshipPosition("DevOps", "Desc", LocalDate.now(), LocalDate.now().plusDays(3), "DevOps", "Req");
        assigned.setAssigned(true);
        TraineeshipPosition expired = new TraineeshipPosition("Legacy", "Desc", LocalDate.now(), LocalDate.now().minusDays(2), "Legacy", "Req");
        expired.setAssigned(true);

        company.setPositions(Arrays.asList(assigned, expired));
        when(companyMapper.findById("gamma")).thenReturn(Optional.of(company));
        List<TraineeshipPosition> result = companyService.retrieveAssignedPositions("gamma");
        assertEquals(1, result.size());
        assertTrue(result.contains(assigned));
    }

    @Test
    @DisplayName("Test evaluateAssignedPosition success and failure")
    void testEvaluateAssignedPosition() {
        Company company = new Company("delta", "Delta SA", "Larissa");
        TraineeshipPosition assigned = new TraineeshipPosition("Data", "Data", LocalDate.now(), LocalDate.now().plusDays(5), "Data", "Req");
        
        assigned.setAssigned(true);
        company.setPositions(Collections.singletonList(assigned));
        when(companyMapper.findById("delta")).thenReturn(Optional.of(company));
        when(positionMapper.findById(anyInt())).thenReturn(Optional.of(assigned));
        assertDoesNotThrow(() -> companyService.evaluateAssignedPosition("delta", 1));

        Company other = new Company("other", "Other Inc", "Volos");
        TraineeshipPosition outside = new TraineeshipPosition("Outside", "No", LocalDate.now(), LocalDate.now().plusDays(10), "Cloud", "Req");
        
        outside.setAssigned(true);
        when(positionMapper.findById(2)).thenReturn(Optional.of(outside));
        when(companyMapper.findById("delta")).thenReturn(Optional.of(company));
        assertThrows(IllegalArgumentException.class, () -> companyService.evaluateAssignedPosition("delta", 2));
    }

    @Test
    @DisplayName("Test saving evaluation to a position")
    void testSaveEvaluation() {
        TraineeshipPosition position = mock(TraineeshipPosition.class);
        Evaluation evaluation = new Evaluation();

        when(positionMapper.findById(1)).thenReturn(Optional.of(position));
        companyService.saveEvaluation(1, evaluation);
        verify(position).addEvaluation(evaluation);
    }

    @Test
    @DisplayName("Test deleting assigned position")
    void testDeleteAssignedPosition() {
        Company company = spy(new Company("theta", "Theta Ltd", "Ioannina"));
        
        when(companyMapper.findById("theta")).thenReturn(Optional.of(company));
        companyService.deleteAssignedPosition("theta", 99);
        verify(company).removePosition(99);
    }

    @Test
    @DisplayName("Test retrieving expired positions")
    void testRetrieveExpiredPositions() {
        Company company = new Company("epsilon", "Epsilon Ltd", "Kavala");
        TraineeshipPosition expired = new TraineeshipPosition("Old", "Desc", LocalDate.now().minusDays(10), LocalDate.now().minusDays(1), "OldTech", "Req");
        TraineeshipPosition future = new TraineeshipPosition("New", "Desc", LocalDate.now(), LocalDate.now().plusDays(5), "NewTech", "Req");
        
        company.setPositions(List.of(expired, future));
        when(companyMapper.findById("epsilon")).thenReturn(Optional.of(company));
        List<TraineeshipPosition> result = companyService.retrieveExpiredPositions("epsilon");
        assertEquals(1, result.size());
        assertTrue(result.contains(expired));
    }
}
