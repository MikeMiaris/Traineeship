package com.example.traineeship.factories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionSearchFactory {
 
    private final SearchBasedOnLocation searchBasedOnLocation;
    
    private final SearchBasedOnInterests searchBasedOnInterests;
    
    private final CompositeSearch compositeSearch;
    
    @Autowired
    public PositionSearchFactory(SearchBasedOnLocation location, SearchBasedOnInterests interests, CompositeSearch composite) {
    	this.searchBasedOnLocation = location;
    	this.searchBasedOnInterests = interests;
    	this.compositeSearch = composite;
    	
    }

    public PositionSearchStrategy create(String strategy) {
        return switch (strategy.toLowerCase()) {
            case "location" ->  searchBasedOnLocation;
            case "interests" -> searchBasedOnInterests;
            case "composite" -> compositeSearch;
            default -> throw new IllegalArgumentException("Invalid strategy: " + strategy);
        };
    }
}