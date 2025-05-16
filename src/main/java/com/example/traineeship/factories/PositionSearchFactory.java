package com.example.traineeship.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionSearchFactory {
    @Autowired
    private SearchBasedOnLocation searchBasedOnLocation;
    
    @Autowired
    private SearchBasedOnInterests searchBasedOnInterests;
    

    public PositionSearchStrategy create(String strategy) {
        return switch (strategy.toLowerCase()) {
            case "location" -> searchBasedOnLocation;
            case "interests" -> searchBasedOnInterests;
            default -> throw new IllegalArgumentException("Invalid strategy: " + strategy);
        };
    }
}