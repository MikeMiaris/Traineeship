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
import org.springframework.test.annotation.Commit;

import com.example.traineeship.domainmodel.Professor;

import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.ProfessorMapper;

import com.example.traineeship.mappers.TraineeshipPositionMapper;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AssignmentBasedOnInterestsTest {
	@Autowired
	ProfessorMapper profmapper;
	@Autowired
    TraineeshipPositionMapper positionMapper;
	@Autowired
	CompanyMapper companyMapper;
    
    private AssignmentBasedOnInterests strategy;

    @BeforeEach
    void setUp() {
        strategy = new AssignmentBasedOnInterests(positionMapper, profmapper);
    }

    @Test
    void search_ShouldReturnPositions_ForValidPosition() {
        Professor user1 = new Professor("user1", "user1", "AI,Soda");
        Professor user2 = new Professor("user2", "user2", "Soda");
        Professor user3 = new Professor("user3", "user3", "Pepsi");
        Professor user4 = new Professor("user4", "user4", "CocaCola,Burritos");
        Professor user5 = new Professor("user5", "user5", "Chimichangas,Strudel");
        
        
        TraineeshipPosition pos1 = new TraineeshipPosition("pos1", "None", LocalDate.now(),LocalDate.now(),"AI,ML","AI");

        
        
        profmapper.save(user1);
        profmapper.save(user2);
        profmapper.save(user3);
        profmapper.save(user4);
        profmapper.save(user5);
        positionMapper.save(pos1);
        
        
        Professor refreshedProf = profmapper.findById(user1.getUsername()).orElseThrow();
        
        

 

        strategy.assign(pos1.getId());
        
        
        
        assertEquals(user1.getProfessorName(),pos1.getSupervisor().getProfessorName());
        assertTrue(pos1.isAssigned());
        assertThat(refreshedProf.getSupervisedPositions()).extracting(TraineeshipPosition::getTitle).contains(pos1.getTitle());
        
        
        
        
    }

    @Test
    void search_ShouldThrow_WhenPositionNotFound() {

        assertThrows(IllegalArgumentException.class, () -> {
            strategy.assign(123123);
        });
    }
}
