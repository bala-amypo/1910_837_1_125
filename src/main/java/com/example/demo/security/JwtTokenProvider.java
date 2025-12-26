package com.example.demo.security;

import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    public String generateToken(Authentication auth, User user) {
        return "jwt-token"; // Placeholder for actual implementation
    }
}