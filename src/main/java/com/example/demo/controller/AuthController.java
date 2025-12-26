package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager am;
    private final JwtTokenProvider jp;
    private final UserService us;
    public AuthController(AuthenticationManager am, JwtTokenProvider jp, UserService us) { this.am = am; this.jp = jp; this.us = us; }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest req) { return new ResponseEntity<>(us.register(req), HttpStatus.CREATED); }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        am.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        User u = us.findByEmailIgnoreCase(req.getEmail());
        String t = jp.generateToken(null, u);
        return ResponseEntity.ok(new AuthResponse(t, u.getEmail(), u.getRole(), u.getId()));
    }
}