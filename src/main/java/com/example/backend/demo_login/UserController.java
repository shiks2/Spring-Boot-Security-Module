package com.example.backend.demo_login;

import com.example.backend.demo_login.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello World";
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
           return userServices.register(user);
    }
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
    }*/
    @PostMapping("/login")
    public String login(@RequestBody Users user){
        System.out.println( "user login details" + user);
        return userServices.verify(user);
        //return "Login Successful";
    }

}
