package com.example.backend.demo_login.User;

import com.example.backend.demo_login.Auth.*;
import com.example.backend.demo_login.Service.JWTService;
import com.example.backend.demo_login.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseEntity.ok(ApiResponse.success("Service is running", "Health check passed"));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        AuthResponse response = userServices.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        AuthResponse response = userServices.login(request);
        return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UserResponse user = userServices.getCurrentUser(username);
        return ResponseEntity.ok(ApiResponse.success(user, "User details retrieved successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        // In a stateless JWT setup, logout is typically handled client-side
        // by removing the token. You could implement token blacklisting here if needed.
        return ResponseEntity.ok(ApiResponse.success(null, "Logout successful"));
    }
}

/*

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private JWTService jwtService;
    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World Sachin";
    }


    @PostMapping("/register")
    public Users register(@RequestBody Users user){
           return userServices.register(user);
    }
        */
/*@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users user) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (userServices.verify(user).equals("Login Successful")) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }
    }*//*


    */
/*@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users user) {
        try {
            String result = userServices.verify(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", result);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (AuthenticationException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }*//*

    @PostMapping("/login")
    public String login(@RequestBody Users user){
        System.out.println( "user login details" + user);
        return userServices.verify(user);
        //return "Login Successful";
    }
}
*/
