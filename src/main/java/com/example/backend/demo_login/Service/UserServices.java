package com.example.backend.demo_login.Service;

import com.example.backend.demo_login.Auth.AuthRequest;
import com.example.backend.demo_login.Auth.AuthResponse;
import com.example.backend.demo_login.Auth.Exception.CustomAuthenticationException;
import com.example.backend.demo_login.Auth.Exception.UserAlreadyExistsException;
import com.example.backend.demo_login.Auth.Exception.UserNotFoundException;
import com.example.backend.demo_login.Auth.Exception.ValidationException;
import com.example.backend.demo_login.Auth.RegisterRequest;
import com.example.backend.demo_login.Auth.UserResponse;
import com.example.backend.demo_login.User.UserRepo;
import com.example.backend.demo_login.User.Users;
import com.example.backend.demo_login.Utilities.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
@Transactional
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registration attempt for username: {}, email: {}", request.getUsername(), request.getEmail());
        
        // Manual validation for MongoDB
        validateRegistrationRequest(request);

        // Check if user already exists
        if (userRepo.existsByUsername(request.getUsername())) {
            log.warn("Registration failed - Username already exists: {}", request.getUsername());
            throw new UserAlreadyExistsException("Username is already taken");
        }

        if (userRepo.existsByEmail(request.getEmail())) {
            log.warn("Registration failed - Email already exists: {}", request.getEmail());
            throw new UserAlreadyExistsException("Email is already registered");
        }

        // Create and save user
        Users user = Users.builder()
                .userId(request.getUsername() + ":" + request.getEmail())
                .username(request.getUsername().trim().toLowerCase())
                .email(request.getEmail().trim().toLowerCase())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .roles(Arrays.asList("USER"))
                .build();

        Users savedUser = userRepo.save(user);
        log.info("Registration successful for user: {}", savedUser.getUsername());

        // Generate token
        String token = jwtService.generateToken(savedUser.getUsername());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .user(mapToUserResponse(savedUser))
                .build();
    }
    
    private void validateRegistrationRequest(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }
        
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        
        if (!ValidationUtils.isValidUsername(request.getUsername())) {
            throw new ValidationException("Username must be 3-20 characters, alphanumeric and underscores only");
        }
        
        if (!ValidationUtils.isValidEmail(request.getEmail())) {
            throw new ValidationException("Please provide a valid email address");
        }
        
        if (!ValidationUtils.isValidPassword(request.getPassword())) {
            throw new ValidationException(ValidationUtils.getPasswordRequirements());
        }
    }
    
    public AuthResponse login(AuthRequest request) {
        log.info("Login attempt for user: {}", request.getUsernameOrEmail());
        
        // Validate request
        if (request.getUsernameOrEmail() == null || request.getUsernameOrEmail().trim().isEmpty()) {
            throw new ValidationException("Username/Email is required");
        }
        
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new ValidationException("Password is required");
        }

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsernameOrEmail().trim(),
                            request.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                Users user = userRepo.findByUsernameOrEmail(request.getUsernameOrEmail().trim())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

                String token = jwtService.generateToken(user.getUsername());
                log.info("Login successful for user: {}", user.getUsername());

                return AuthResponse.builder()
                        .token(token)
                        .tokenType("Bearer")
                        .expiresIn(jwtService.getExpirationTime())
                        .user(mapToUserResponse(user))
                        .build();
            }

            throw new CustomAuthenticationException("Authentication failed");

        } catch (BadCredentialsException e) {
            log.warn("Login failed for user: {} - Invalid credentials", request.getUsernameOrEmail());
            throw new CustomAuthenticationException("Invalid username/email or password");
        } catch (AuthenticationException e) {
            log.warn("Login failed for user: {} - {}", request.getUsernameOrEmail(), e.getMessage());
            throw new CustomAuthenticationException("Authentication failed: " + e.getMessage());
        }
    }

    public UserResponse getCurrentUser(String username) {
        log.debug("Getting current user: {}", username);
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToUserResponse(user);
    }

    private UserResponse mapToUserResponse(Users user) {
        return UserResponse.builder()
                .id(String.valueOf(user.getId()))
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .profilePicUrl(user.getProfilePicUrl())
                .build();
    }
    public String verify(Users user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
                //return "success";
            }
            throw new AuthenticationException("Authentication failed") {};

        } catch (AuthenticationException e) {
            // Re-throw the exception to be handled by the controller
            throw e;
        }
    }
}
 