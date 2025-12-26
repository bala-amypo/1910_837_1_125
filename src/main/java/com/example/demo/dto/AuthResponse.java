package com.example.demo.dto;

public class AuthResponse {
    private String token;
    private String email;
    private String role;
    private Long id;

    public AuthResponse() {}
    public AuthResponse(String token, String email, String role, Long id) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.id = id;
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public Long getId() { return id; }
}