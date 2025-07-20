package com.example.backend.demo_login.Enum;

public enum RoutineFrequency {
    ONCE_A_DAY("Once a day"),
    TWICE_A_DAY("Twice a day"),
    THRICE_A_DAY("Thrice a day"),
    ONCE_A_WEEK("Once a week"),
    BI_WEEKLY("Bi-weekly"),
    ONCE_A_MONTH("Once a month");

    private final String description;

    // Constructor to assign description to each enum constant
    RoutineFrequency(String description) {
        this.description = description;
    }

    // Method to get the description of the enum
    public String getDescription() {
        return description;
    }
}


