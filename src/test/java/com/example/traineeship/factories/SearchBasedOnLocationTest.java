package com.example.traineeship.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class SearchBasedOnLocationTest {
	@Autowired
	StudentMapper studentMapper;
	@Autowired
    TraineeshipPositionMapper positionMapper;
    
    private SearchBasedOnLocation strategy;

    @BeforeEach
    void setUp() {
        strategy = new SearchBasedOnLocation(studentMapper, positionMapper);
    }

    @Test
    void search_ShouldReturnPositions_ForValidStudent() {
        Student user1 = new Student("user1", "user1", "0000", 1.1, "Bangladesh", "Soda", "None", true, null);
        
        
        TraineeshipPosition pos1 = new TraineeshipPosition("Pos1", "None", LocalDate.now(),LocalDate.now(),"AI","AI");
        TraineeshipPosition pos2 = new TraineeshipPosition("Pos2", "None", LocalDate.now(),LocalDate.now(),"AI","AI");

        
        studentMapper.save(user1);
        positionMapper.save(pos1);
        positionMapper.save(pos2);

        //when(studentMapper.findById(user1.getUsername()))
           // .thenReturn(Optional.of(user1));
        //when(positionMapper.findByLocation("Bangladesh"))
            //.thenReturn(expectedPositions);

        // Act
        List<TraineeshipPosition> result = strategy.search(user1.getUsername());

        // Assert
        assertEquals(2, result.size());
        verify(studentMapper).findById(user1.getUsername());
        verify(positionMapper).findByLocation("Bangladesh");
    }

    /*@Test
    void search_ShouldThrow_WhenStudentNotFound() {
        when(studentMapper.findById("unknown"))
            .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            strategy.search("unknown");
        });
    }

    @Test
    void search_ShouldReturnEmptyList_WhenNoLocation() {
        Student student = new Student(); // No location set
        when(studentMapper.findById("student123"))
            .thenReturn(Optional.of(student));

        List<TraineeshipPosition> result = strategy.search("student123");
        assertTrue(result.isEmpty());
    }*/
}
