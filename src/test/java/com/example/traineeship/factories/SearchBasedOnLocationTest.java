package com.example.traineeship.factories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.time.LocalDate;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
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
	@Autowired
	CompanyMapper companyMapper;
    
    private SearchBasedOnLocation strategy;

    @BeforeEach
    void setUp() {
        strategy = new SearchBasedOnLocation(studentMapper, positionMapper);
    }

    @Test
    void search_ShouldReturnPositions_ForValidStudent() {
        Student user1 = new Student("user1", "user1", "0000", 1.1, "Bangladesh", "Soda", "None", true, null);
        Company comp = new Company("comp1","crapcorp","Bangladesh");
        Company comp2 = new Company("comp2","shitcorp","London");
        
        
        TraineeshipPosition pos1 = new TraineeshipPosition("pos1", "None", LocalDate.now(),LocalDate.now(),"AI","AI");
        TraineeshipPosition pos2 = new TraineeshipPosition("pos2", "None", LocalDate.now(),LocalDate.now(),"AI","AI");
        TraineeshipPosition pos3 = new TraineeshipPosition("pos3", "None", LocalDate.now(),LocalDate.now(),"AI","AI");
        
        
        companyMapper.save(comp);
        companyMapper.save(comp2);
        
        studentMapper.save(user1);
        
        positionMapper.save(pos1);
        positionMapper.save(pos2);
        
        
 
        pos1.setCompany(comp);
        pos2.setCompany(comp);
        pos3.setCompany(comp2);
        
        //companyMapper.flush();
        
        List<TraineeshipPosition> result = strategy.search(user1.getUsername());


        assertEquals(2, result.size());
        assertThat(result).hasSize(2).extracting(TraineeshipPosition::getTitle).containsExactly("pos1","pos2");
        
        
    }

    @Test
    void search_ShouldThrow_WhenStudentNotFound() {

        assertThrows(IllegalArgumentException.class, () -> {
            strategy.search("unknown");
        });
    }

    @Test
    void search_ShouldReturnEmptyList_WhenNoLocation() {
    	Student user1 = new Student("user1", "user1", "0000", 1.1, "Bangladesh", "Soda", "None", true, null);
    	studentMapper.save(user1);

        List<TraineeshipPosition> result = strategy.search(user1.getUsername());
        assertTrue(result.isEmpty());
    }
}
