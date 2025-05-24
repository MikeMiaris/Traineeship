package com.example.traineeship.services;

import com.example.traineeship.domainmodel.Student;
import com.example.traineeship.domainmodel.TraineeshipPosition;
import com.example.traineeship.mappers.StudentMapper;
import com.example.traineeship.mappers.TraineeshipPositionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private TraineeshipPositionMapper positionMapper;

    @InjectMocks
    private StudentServiceImpl service;

    @BeforeEach
    void setUp() {
    	studentMapper = mock(StudentMapper.class);
        positionMapper = mock(TraineeshipPositionMapper.class);
        service = new StudentServiceImpl(studentMapper, positionMapper);
    }
    
    @Test
    @DisplayName("saveProfile test")
    void saveProfile_test() {
        Student s = new Student();
        service.saveProfile(s);
        verify(studentMapper).save(s);
    }

    @Test
    @DisplayName("retrieveProfile returns student when found")
    void retrieveProfile_test() {
        Student expected = new Student();
        expected.setUsername("stu1");
        when(studentMapper.findById("stu1")).thenReturn(Optional.of(expected));

        Student actual = service.retrieveProfile("stu1");

        assertThat(actual).isSameAs(expected);
        verify(studentMapper).findById("stu1");
    }

    @Test
    @DisplayName("retrieveProfile throws if student not found")
    void retrieveProfile_test1() {
        when(studentMapper.findById("missing")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.retrieveProfile("missing")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Student not found: missing");
        verify(studentMapper).findById("missing");
    }

    @Test
    @DisplayName("saveLogbook test")
    void saveLogbook_test() {
        TraineeshipPosition pos = new TraineeshipPosition();
        service.saveLogbook(pos);
        verify(positionMapper).save(pos);
    }
}
