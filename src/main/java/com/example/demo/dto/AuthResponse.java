package com.example.demo.dto;
public class AuthResponse {
    private String token, email, role;
    private Long id;
    public AuthResponse(String t, String e, String r, Long i) {
        this.token = t; this.email = e; this.role = r; this.id = i;
    }
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Long getId() { return id; }
}