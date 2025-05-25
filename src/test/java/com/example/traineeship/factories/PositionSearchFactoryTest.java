package com.example.traineeship.factories;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

public class PositionSearchFactoryTest {
	@Mock SearchBasedOnLocation locsearch;
    @Mock SearchBasedOnInterests intsearch;
    @Mock CompositeSearch compsearch;
    private PositionSearchFactory factory;
    
    @BeforeEach
    void setUp() {
        factory = new PositionSearchFactory(locsearch,intsearch,compsearch);
    }
    
    @Test
    void create_returnsSearchBasedOnLocation() {
        PositionSearchStrategy strategy = factory.create("location");
        assertSame(locsearch, strategy);
    }

    @Test
    void create_returnsSearchBasedOnInterests() {
        PositionSearchStrategy strategy = factory.create("interests");
        assertSame(intsearch, strategy);
    }

    @Test
    void create_returnsCompositeStrategy() {
        PositionSearchStrategy strategy = factory.create("composite");
        assertSame(compsearch, strategy);
    }

    @Test
    void create_ThrowsExceptionForInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.create("invalid");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"LOCATION", "Location", "lOcAtIoN"})
    void create_ShouldBeCaseInsensitive(String strategy) {
        PositionSearchStrategy result = factory.create(strategy);
        assertSame(locsearch, result);
    }
}
