package com.example.backend.demo_login.Auth;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDetails {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> validationErrors;
}
