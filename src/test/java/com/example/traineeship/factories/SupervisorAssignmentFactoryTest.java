package com.example.traineeship.factories;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

public class SupervisorAssignmentFactoryTest {
	@Mock AssignmentBasedOnLoad loadass;
    @Mock AssignmentBasedOnInterests intass;
    private SupervisorAssignmentFactory factory;
    
    @BeforeEach
    void setUp() {
        factory = new SupervisorAssignmentFactory(loadass,intass);
    }
    
    @Test
    void create_returnsAssignmentBasedOnLoad() {
        SupervisorAssignmentStrategy strategy = factory.create("load");
        assertSame(loadass, strategy);
    }

    @Test
    void create_returnsAssignmentBasedOnInterests() {
    	SupervisorAssignmentStrategy strategy = factory.create("interests");
        assertSame(intass, strategy);
    }


    @Test
    void create_ThrowsExceptionForInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create("invalid");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"LOAD", "LoAd", "load"})
    void create_ShouldBeCaseInsensitive(String strategy) {
    	SupervisorAssignmentStrategy result = factory.create(strategy);
        assertSame(loadass, result);
    }
}
