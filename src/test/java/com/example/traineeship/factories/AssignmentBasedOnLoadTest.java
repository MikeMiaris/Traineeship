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

import com.example.traineeship.domainmodel.Company;
import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.CompanyMapper;
import com.example.traineeship.mappers.ProfessorMapper;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;

import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AssignmentBasedOnLoadTest {
	@Autowired
	ProfessorMapper profmapper;
	@Autowired
    TraineeshipPositionMapper positionMapper;
	@Autowired
	CompanyMapper companyMapper;
    
    private AssignmentBasedOnLoad strategy;

    @BeforeEach
    void setUp() {
        strategy = new AssignmentBasedOnLoad(positionMapper, profmapper);
    }

    @Test
    void search_ShouldReturnPositions_ForValidStudent() {
        Professor user1 = new Professor("user1", "user1", "Soda");
        Professor user2 = new Professor("user2", "user2", "Soda");
        Professor user3 = new Professor("user3", "user3", "Soda");
        Professor user4 = new Professor("user4", "user4", "Soda");
        Professor user5 = new Professor("user5", "user5", "Soda");
        
        
        TraineeshipPosition pos1 = new TraineeshipPosition("pos1", "None", LocalDate.now(),LocalDate.now(),"Soda,AI","AI");
        TraineeshipPosition pos2 = new TraineeshipPosition("pos2", "None", LocalDate.now(),LocalDate.now(),"Soda","AI");
        TraineeshipPosition pos3 = new TraineeshipPosition("pos3", "None", LocalDate.now(),LocalDate.now(),"AI","AI");
        TraineeshipPosition pos4 = new TraineeshipPosition("pos4", "None", LocalDate.now(),LocalDate.now(),"Soda,AI,doodoo","AI");
        TraineeshipPosition pos5 = new TraineeshipPosition("pos5", "None", LocalDate.now(),LocalDate.now(),"Soda,AI,ML","AI");
        TraineeshipPosition pos6 = new TraineeshipPosition("pos6", "None", LocalDate.now(),LocalDate.now(),"ML","AI");
        TraineeshipPosition pos7 = new TraineeshipPosition("pos7", "None", LocalDate.now(),LocalDate.now(),"SQL","AI");
        TraineeshipPosition pos8 = new TraineeshipPosition("pos8", "None", LocalDate.now(),LocalDate.now(),"SQL,Javaspring","AI");
        TraineeshipPosition pos9 = new TraineeshipPosition("pos9", "None", LocalDate.now(),LocalDate.now(),"Soda,SQL","AI");
        TraineeshipPosition pos10 = new TraineeshipPosition("pos10", "None", LocalDate.now(),LocalDate.now(),"Soda,Javaspring","AI");
        
        
        
        user1.addSupervisedPosition(pos1);
        user2.addSupervisedPosition(pos2);
        user2.addSupervisedPosition(pos3);
        user3.addSupervisedPosition(pos4);
        user3.addSupervisedPosition(pos5);
        user3.addSupervisedPosition(pos6);
        user4.addSupervisedPosition(pos7);
        user4.addSupervisedPosition(pos8);
        user4.addSupervisedPosition(pos9);
        
        pos1.setSupervisor(user1);
        pos2.setSupervisor(user2);
        pos3.setSupervisor(user2);
        pos4.setSupervisor(user3);
        pos5.setSupervisor(user3);
        pos6.setSupervisor(user3);
        pos7.setSupervisor(user4);
        pos8.setSupervisor(user4);
        pos9.setSupervisor(user4);
        
        
        
        
     
        
        
        profmapper.save(user5);
        positionMapper.save(pos1);
        positionMapper.save(pos2);
        positionMapper.save(pos3);
        positionMapper.save(pos4);
        positionMapper.save(pos5);
        positionMapper.save(pos6);
        positionMapper.save(pos7);
        positionMapper.save(pos8);
        positionMapper.save(pos9);
        positionMapper.save(pos10);
        
        //profmapper.flush();
        //positionMapper.flush();
        
        Professor refreshedProf = profmapper.findById(user5.getUsername()).orElseThrow();
        
        

 

        strategy.assign(pos10.getId());
        
        
        
        assertEquals(user5.getProfessorName(),pos10.getSupervisor().getProfessorName());
        assertTrue(pos10.isAssigned());
        assertThat(refreshedProf.getSupervisedPositions()).extracting(TraineeshipPosition::getTitle).contains(pos10.getTitle());
        
        
        
        
    }

    /*@Test
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
    }*/
}
