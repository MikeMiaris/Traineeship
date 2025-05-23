package com.example.traineeship.mappers;

import com.example.traineeship.domainmodel.Professor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProfessorMapperTest {

    @Autowired
    private ProfessorMapper professorMapper;

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
}