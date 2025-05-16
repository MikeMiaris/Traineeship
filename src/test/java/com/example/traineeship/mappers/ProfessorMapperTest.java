package com.example.traineeship.mappers;

import com.example.traineeship.domainmodel.Professor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class ProfessorMapperTest {

    @Autowired
    private ProfessorMapper professorMapper;

    @Test
    void saveAndFindByUsername() {
        // given
        Professor prof = new Professor();
        prof.setUsername("prof1");
        prof.setProfessorName("Dr. Test");
        prof.setInterests("AI,ML");
        professorMapper.save(prof);

        // when
        Professor loaded = professorMapper.findByUsername("prof1");

        // then
        assertThat(loaded).isNotNull();
        assertThat(loaded.getProfessorName()).isEqualTo("Dr. Test");
        assertThat(loaded.getInterests()).contains("ML");
    }
}
