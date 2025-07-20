package com.example.backend.demo_login.Auth.Exception;

public class CustomAuthenticationException extends RuntimeException {
    public CustomAuthenticationException(String message) {
        super(message);
    }
  public CustomAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
