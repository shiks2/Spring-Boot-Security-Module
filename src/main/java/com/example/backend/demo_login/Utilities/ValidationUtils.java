package com.example.backend.demo_login.Utilities;

import java.util.regex.Pattern;

public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_]{3,20}$");

    private static final Pattern PASSWORD_PATTERN = 
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,100}$");

    public static boolean isValidEmail(String email) {
        return email != null && 
               !email.trim().isEmpty() && 
               EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null && 
               !username.trim().isEmpty() && 
               USERNAME_PATTERN.matcher(username.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && 
               !password.isEmpty() &&
               password.length() >= 8 && 
               password.length() <= 100 &&
               PASSWORD_PATTERN.matcher(password).matches();
    }

    public static String getPasswordRequirements() {
        return "Password must be 8-100 characters and contain: uppercase letter, lowercase letter, digit, and special character (@$!%*?&)";
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
