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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServices {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    public AuthResponse register(RegisterRequest request) {
        // Validate request
        if (!request.isValid()) {
            throw new ValidationException(request.getValidationMessage());
        }

        // Check if username already exists
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        // Check if email already exists
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered");
        }

        // Create new user
        Users user = new Users();
        user.setUserId(request.getUsername() + ":" + request.getEmail());
        user.setUsername(request.getUsername().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRoles(Arrays.asList("USER")); // Default role

        Users savedUser = userRepo.save(user);

        // Generate token
        String token = jwtService.generateToken(savedUser.getUsername());

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresIn(jwtService.getExpirationTime())
                .user(mapToUserResponse(savedUser))
                .build();
    }

    /*public Users register(Users user){
        String user_id = user.getUsername() + ":"+ user.getEmail();
        user.setUserId(user_id);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);

    }*/
    public AuthResponse login(AuthRequest request) {
        // Validate request
        if (!request.isValid()) {
            throw new ValidationException(request.getValidationMessage());
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

                return AuthResponse.builder()
                        .token(token)
                        .tokenType("Bearer")
                        .expiresIn(jwtService.getExpirationTime())
                        .user(mapToUserResponse(user))
                        .build();
            }

            throw new CustomAuthenticationException("Authentication failed");

        } catch (BadCredentialsException e) {
            throw new CustomAuthenticationException("Invalid username/email or password");
        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException("Authentication failed: " + e.getMessage());
        }
    }

    /*public UserResponse getCurrentUser(String username) {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToUserResponse(user);
    }*/

    private UserResponse mapToUserResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
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
 