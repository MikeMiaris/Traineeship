package com.example.traineeship.mappers;

import com.example.traineeship.domainmodel.Professor;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ProfessorMapperTest {

    @Autowired
    private ProfessorMapper professorMapper;
    
    @Autowired 
    TraineeshipPositionMapper positionMapper;

    @Test
    @DisplayName("Test saving and retrieving a professor by interest")
    public void testFindByInterest() {
    	
        Professor p1 = new Professor("user1", "Dr. Alpha", "AI,ML");
        Professor p2 = new Professor("user2", "Dr. Beta", "Cybersecurity");
        
        professorMapper.save(p1);
        professorMapper.save(p2);
        
        List<Professor> result = professorMapper.findByInterest("AI");

        assertThat(result).isNotEmpty();
        assertThat(result).extracting(Professor::getUsername).contains("user1");
    }
    
  
    @Test
    @DisplayName("Test finding professor with least supervised positions")
    public void testFindByLoad() {

        Professor p1 = new Professor("user1", "Dr. Alpha", "AI,ML");
        Professor p2 = new Professor("user2", "Dr. Beta", "Cybersecurity");
        
        TraineeshipPosition pos1 = new TraineeshipPosition("pos1","s", LocalDate.now() ,LocalDate.now() ,"AI","pos");
        TraineeshipPosition pos2 = new TraineeshipPosition("pos2","s", LocalDate.now(),LocalDate.now(),"AI","pos");
        TraineeshipPosition pos3 = new TraineeshipPosition("pos3","s", LocalDate.now(),LocalDate.now(),"AI","pos");
        
        pos1.setSupervisor(p1);
        p1.addSupervisedPosition(pos1);
        
        pos2.setSupervisor(p2);
        pos3.setSupervisor(p2);
        p2.addSupervisedPosition(pos2);
        p2.addSupervisedPosition(pos3);
        
        professorMapper.save(p1);
        professorMapper.save(p2);
        positionMapper.save(pos1);
        positionMapper.save(pos2);
        positionMapper.save(pos3);
        
        Professor result = professorMapper.findByLoad();
        
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("user1");
        assertThat(result.getSupervisedPositions()).hasSize(1);
    }
    
    
}