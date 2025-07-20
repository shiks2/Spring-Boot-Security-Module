package com.example.backend.demo_login.Auth;

import lombok.Data;

import java.util.regex.Pattern;
@Data

public class RegisterRequest {
    private String username;
    private String password;
    private String email;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    // Custom validation method
    public boolean isValid() {
        return isUsernameValid() && isPasswordValid() && isEmailValid();
    }

    public String getValidationMessage() {
        if (!isUsernameValid()) {
            if (username == null || username.trim().isEmpty()) {
                return "Username is required";
            }
            if (username.length() < 3 || username.length() > 20) {
                return "Username must be between 3 and 20 characters";
            }
        }

        if (!isPasswordValid()) {
            if (password == null || password.trim().isEmpty()) {
                return "Password is required";
            }
            if (password.length() < 6 || password.length() > 100) {
                return "Password must be between 6 and 100 characters";
            }
        }

        if (!isEmailValid()) {
            if (email == null || email.trim().isEmpty()) {
                return "Email is required";
            }
            return "Please provide a valid email address";
        }

        return null;
    }

    private boolean isUsernameValid() {
        return username != null && !username.trim().isEmpty() &&
                username.length() >= 3 && username.length() <= 20;
    }

    private boolean isPasswordValid() {
        return password != null && !password.trim().isEmpty() &&
                password.length() >= 6 && password.length() <= 100;
    }

    private boolean isEmailValid() {
        return email != null && !email.trim().isEmpty() &&
                EMAIL_PATTERN.matcher(email).matches();
    }
}
