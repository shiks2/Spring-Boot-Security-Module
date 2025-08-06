package com.example.backend.demo_login.Enum;

public enum RoutineStatus {
    ACTIVE("Active"),
    PAUSED("Paused"),
    DISCONTINUED("Discontinued"),
    DRAFT("Draft");
    
    private final String description;
    
    RoutineStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}