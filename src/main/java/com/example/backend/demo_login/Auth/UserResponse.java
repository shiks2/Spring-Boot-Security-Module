package com.example.backend.demo_login.Auth;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class UserResponse {
    private String id;
    private String userId;
    private String username;
    private String email;
    private List<String> roles;
    private String profilePicUrl;
}
