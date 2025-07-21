package com.example.backend.demo_login.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    
    @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:58566}")
    private String[] allowedOrigins;
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Only API endpoints
                        .allowedOrigins(allowedOrigins) // Configurable origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders(
                            "Authorization", 
                            "Content-Type", 
                            "X-Requested-With", 
                            "Accept",
                            "Origin"
                        )
                        .exposedHeaders("Authorization") // Expose auth headers
                        .allowCredentials(true) // Allow cookies/auth headers
                        .maxAge(3600); // Cache preflight for 1 hour
            }
        };
    }
}
