package com.example.backend.demo_login.Enum;

public enum RoutineType {
    SKIN("Skin Care"),
    HAIR("Hair Care"),
    FACE("Face Care");

    private final String description;

    // Constructor to assign description
    RoutineType(String description) {
        this.description = description;
    }

    // Method to retrieve description
    public String getDescription() {
        return description;
    }
}
