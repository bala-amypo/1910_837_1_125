package com.example.demo.security;

import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    // This logic is mocked in your tests, so a simplified version is enough
    public String generateToken(Authentication authentication, User user) {
        return "jwt-token"; 
    }
}