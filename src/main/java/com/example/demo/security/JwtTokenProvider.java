package com.example.demo.security;

import com.example.demo.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public JwtTokenProvider() {
    }

    public String generateToken(Authentication authentication, User user) {
        // TESTS MOCK THIS METHOD – real JWT not required
        return "jwt-token";
    }
}
