package com.example.traineeship.domainmodel;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class StudentModelTest {

    @Test
    void testNoArgConstructorAndDefaults() {
        Student s = new Student();
        assertNull(s.getUsername(), "username should be null by default");
        assertNull(s.getStudentName(), "studentName should be null by default");
        assertNull(s.getAM(), "AM should be null by default");
        assertEquals(0.0, s.getAvgGrade(), "avgGrade should default to 0.0");
        assertNull(s.getPreferredLocation(), "preferredLocation should be null by default");
        assertNull(s.getInterests(), "interests should be null by default");
        assertNull(s.getSkills(), "skills should be null by default");
        assertFalse(s.isLookingForTraineeship(), "lookingForTraineeship should default to false");
        assertNull(s.getAssignedTraineeship(), "assignedTraineeship should be null by default");
    }

    @Test
    void testAllArgsConstructorAndGetters() {
        TraineeshipPosition tp = new TraineeshipPosition();
        LocalDate now = LocalDate.now();
        tp.setId(123);
        tp.setTitle("Title");
        tp.setFromDate(now);
        Student s = new Student("user42","Alice","12345",8.5,"Athens","Java,SQL","Spring,Git", true,tp);

        assertEquals("user42", s.getUsername());
        assertEquals("Alice", s.getStudentName());
        assertEquals("12345", s.getAM());
        assertEquals(8.5, s.getAvgGrade());
        assertEquals("Athens", s.getPreferredLocation());
        assertEquals("Java,SQL", s.getInterests());
        assertEquals("Spring,Git", s.getSkills());
        assertTrue(s.isLookingForTraineeship());
        assertSame(tp, s.getAssignedTraineeship());
    }

    @Test
    void testSetters() {
        Student s = new Student();
        TraineeshipPosition tp = new TraineeshipPosition();
        tp.setId(99);

        s.setUsername("bob");
        s.setStudentName("Bob");
        s.setAM("AM001");
        s.setAvgGrade(9.2);
        s.setPreferredLocation("Berlin");
        s.setInterests("AI");
        s.setSkills("Docker");
        s.setLookingForTraineeship(true);
        s.setAssignedTraineeship(tp);

        assertEquals("bob", s.getUsername());
        assertEquals("Bob", s.getStudentName());
        assertEquals("AM001", s.getAM());
        assertEquals(9.2, s.getAvgGrade());
        assertEquals("Berlin", s.getPreferredLocation());
        assertEquals("AI", s.getInterests());
        assertEquals("Docker", s.getSkills());
        assertTrue(s.isLookingForTraineeship());
        assertEquals(99, s.getAssignedTraineeship().getId());
    }
}
