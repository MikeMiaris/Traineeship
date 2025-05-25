package com.example.traineeship.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.factories.AssignmentBasedOnInterests;
import com.example.traineeship.factories.AssignmentBasedOnLoad;
import com.example.traineeship.factories.CompositeSearch;
import com.example.traineeship.factories.PositionSearchFactory;
import com.example.traineeship.factories.SearchBasedOnInterests;
import com.example.traineeship.factories.SearchBasedOnLocation;
import com.example.traineeship.factories.SupervisorAssignmentFactory;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import com.example.traineeship.domainmodel.Evaluation;
import com.example.traineeship.domainmodel.EvaluationType;


public class CommitteeServiceTest {
    private StudentMapper studentMapper;
    private TraineeshipPositionMapper positionMapper;
    private CommitteeServiceImpl committeeService;
    private PositionSearchFactory positionSearchFactory;
    private SupervisorAssignmentFactory supervisorAssignmentFactory;
    private AssignmentBasedOnLoad loadass;
    private AssignmentBasedOnInterests intass;
    private CompanyMapper companyMapper;
    private SearchBasedOnLocation locsearch;
    private SearchBasedOnInterests intsearch;
    private CompositeSearch compsearch;
    
    @BeforeEach
    void setUp() {
        studentMapper = mock(StudentMapper.class);
    	positionMapper = mock(TraineeshipPositionMapper.class);
    	
    	positionSearchFactory = mock(PositionSearchFactory.class);
    	locsearch = mock(SearchBasedOnLocation.class);
        intsearch = mock(SearchBasedOnInterests.class);
        compsearch = mock(CompositeSearch.class);
        
        supervisorAssignmentFactory = mock(SupervisorAssignmentFactory.class);
        loadass = mock(AssignmentBasedOnLoad.class);
        intass = mock(AssignmentBasedOnInterests.class);
        
        committeeService = new CommitteeServiceImpl(
            positionSearchFactory, 
            supervisorAssignmentFactory, 
            studentMapper, 
            positionMapper
        );
    }
    
    @Test
    void testRetrievePositionsForApplicant() {
    	
        Student user1 = new Student("user1", "user1", "0000", 1.1, "London", "Soda", "None", true, null);
        TraineeshipPosition pos1 = new TraineeshipPosition("pos1", "desc", LocalDate.now(), LocalDate.now(), "Soda", "None");
        TraineeshipPosition pos2 = new TraineeshipPosition("pos2", "desc", LocalDate.now(), LocalDate.now(), "AI", "None");
        TraineeshipPosition pos3 = new TraineeshipPosition("pos3", "desc", LocalDate.now(), LocalDate.now(), "Soda", "None");

        
        

        when(positionSearchFactory.create("location")).thenReturn(locsearch);
        when(positionSearchFactory.create("interests")).thenReturn(intsearch);
        when(positionSearchFactory.create("composite")).thenReturn(compsearch);
        

        when(locsearch.search("user1")).thenReturn(List.of(pos1, pos2)); 
        when(intsearch.search("user1")).thenReturn(List.of(pos1, pos3)); 
        when(compsearch.search("user1")).thenReturn(List.of(pos1)); 
        

        List<TraineeshipPosition> locationResults = committeeService.retrievePositionsForApplicant("user1", "location");
        List<TraineeshipPosition> interestResults = committeeService.retrievePositionsForApplicant("user1", "interests");
        List<TraineeshipPosition> compositeResults = committeeService.retrievePositionsForApplicant("user1", "composite");
        

        assertThat(locationResults)
            .hasSize(2)
            .extracting(TraineeshipPosition::getTitle)
            .containsExactly("pos1", "pos2");
            
        assertThat(interestResults)
            .hasSize(2)
            .extracting(TraineeshipPosition::getTitle)
            .containsExactly("pos1", "pos3");
            
        assertThat(compositeResults)
            .hasSize(1)
            .extracting(TraineeshipPosition::getTitle)
            .containsExactly("pos1");
        

        verify(positionSearchFactory).create("location");
        verify(positionSearchFactory).create("interests");
        verify(positionSearchFactory).create("composite");

    }
    
    @Test
    void testretrievetraineeshipApplications() {
    	Student user1 = new Student("user1", "user1", "0001", 1.1, "London", "Soda", "None", true, null);
    	Student user2 = new Student("user2", "user2", "0002", 1.1, "London", "Soda", "None", true, null);
    	Student user3 = new Student("user3", "user3", "0003", 1.1, "London", "Soda", "None", false, null);
    	Student user4 = new Student("user4", "user4", "0004", 1.1, "London", "Soda", "None", true, null);
    	
    	when(studentMapper.LookingForTrain()).thenReturn(List.of(user1, user2,user4)); 
    	
    	List<Student> applicants = committeeService.retrieveTraineeShipApplications();
    	
    	assertThat(applicants)
        .hasSize(3)
        .extracting(Student::getStudentName)
        .containsExactly("user1","user2","user4");
    	
    }
    
    @Test
    void testassignPosition() {

        Integer positionId = 1;
        String studentUsername = "student123";
        
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(positionId);
        
        Student student = new Student();
        student.setUsername(studentUsername);


        when(positionMapper.findById(positionId))
            .thenReturn(Optional.of(position));
        when(studentMapper.findById(studentUsername))
            .thenReturn(Optional.of(student));


        committeeService.assignPosition(positionId, studentUsername);


        assertTrue(position.isAssigned());
        assertEquals(student, position.getStudent());
        assertEquals(position, student.getAssignedTraineeship());
        

        verify(positionMapper).findById(positionId);
        verify(studentMapper).findById(studentUsername);
    }
    
    @Test
    void testassignSupervisor() {
 
        Integer positionId = 1;
        String strategy = "load";
        
        when(supervisorAssignmentFactory.create("load"))
            .thenReturn(loadass);
        
        when(supervisorAssignmentFactory.create("interests"))
        .thenReturn(intass);


        committeeService.assignSupervisor(positionId, strategy);
        
        verify(supervisorAssignmentFactory).create(strategy);
        verify(loadass).assign(positionId);
        
        strategy = "interests";
        		
        committeeService.assignSupervisor(positionId, strategy);


        verify(supervisorAssignmentFactory).create(strategy);
        verify(intass).assign(positionId);
    }
    
    
    @Test
    void testlistAssignedTraineeships() {
        // Given
        TraineeshipPosition pos1 = new TraineeshipPosition();
        pos1.setId(1);
        pos1.setAssigned(true);
        
        TraineeshipPosition pos2 = new TraineeshipPosition();
        pos2.setId(2);
        pos2.setAssigned(true);

        List<TraineeshipPosition> mockPositions = List.of(pos1, pos2);
        
        when(positionMapper.getallAssigned())
            .thenReturn(mockPositions);


        List<TraineeshipPosition> result = committeeService.listAssignedTraineeships();

  
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(TraineeshipPosition::isAssigned));
        verify(positionMapper).getallAssigned();
    }
    
    
    @Test
    void testcompleteAssignedTraineeships_whenPass() {
        // Given
        Integer positionId = 1;
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(positionId);
        
        List<Evaluation> evals = new ArrayList<>();
        
        Evaluation eval1 = new Evaluation(EvaluationType.COMPANY, 5, 5, 5 );
        Evaluation eval2 = new Evaluation(EvaluationType.COMMITTEE, 4, 4, 4 );
        Evaluation eval3 = new Evaluation(EvaluationType.PROFESSOR, 3, 3, 3 );
        evals.add(eval1);
        evals.add(eval2);
        evals.add(eval3);
        
        position.setEvaluations(evals);

        when(positionMapper.findById(positionId))
            .thenReturn(Optional.of(position));

        // When
        committeeService.completeAssignedTraineeships(positionId);

        // Then
        assertTrue(position.isPassFailGrade());
        verify(positionMapper).findById(positionId);
    }
    
    @Test
    void testcompleteAssignedTraineeships_whenFail() {
        // Given
        Integer positionId = 1;
        TraineeshipPosition position = new TraineeshipPosition();
        position.setId(positionId);
        
        List<Evaluation> evals = new ArrayList<>();
        
        Evaluation eval1 = new Evaluation(EvaluationType.COMPANY, 3, 3, 3 );
        Evaluation eval2 = new Evaluation(EvaluationType.COMMITTEE, 2, 2, 2 );
        Evaluation eval3 = new Evaluation(EvaluationType.PROFESSOR, 1, 1, 1 );
        evals.add(eval1);
        evals.add(eval2);
        evals.add(eval3);
        
        position.setEvaluations(evals);

        when(positionMapper.findById(positionId))
            .thenReturn(Optional.of(position));

        // When
        committeeService.completeAssignedTraineeships(positionId);

        // Then
        assertFalse(position.isPassFailGrade());
        verify(positionMapper).findById(positionId);
    }
    
    
}
