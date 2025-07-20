package com.example.backend.demo_login.Auth;

import lombok.Data;

@Data

public class AuthRequest {
    private String usernameOrEmail;
    private String password;

    // Custom validation method
    public boolean isValid() {
        return usernameOrEmail != null && !usernameOrEmail.trim().isEmpty() &&
                password != null && !password.trim().isEmpty();
    }

    public String getValidationMessage() {
        if (usernameOrEmail == null || usernameOrEmail.trim().isEmpty()) {
            return "Username or email is required";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password is required";
        }
        return null;
    }
}
