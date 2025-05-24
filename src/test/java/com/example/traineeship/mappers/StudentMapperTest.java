package com.example.traineeship.mappers;

import com.example.traineeship.domainmodel.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    @DisplayName("findLookingForTrain should return only students with lookingForTraineeship = true")
    void testLookingForTrain(){
        Student s1 = new Student("alice", "Alice A", "AM123", 8.2,"Paris", "Java,SQL", "Git,Spring",true,null);
        Student s2 = new Student("bob", "Bob B", "AM456", 7.9,"Berlin", "Python", "Docker",false,null);
        studentMapper.save(s1);
        studentMapper.save(s2);

        List<Student> result = studentMapper.LookingForTrain();

        assertThat(result).hasSize(1).extracting(Student::getUsername).containsExactly("alice");
    }
    
}
