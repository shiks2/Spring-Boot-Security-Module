package com.example.backend.demo_login.Auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String tokenType;
    private Long expiresIn;
    private UserResponse user;
}
