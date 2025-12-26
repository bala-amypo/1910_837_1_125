package com.example.demo.dto;
public class AuthRequest {
    private String email, password;
    public AuthRequest() {}
    public String getEmail() { return email; }
    public void setEmail(String s) { this.email = s; }
    public String getPassword() { return password; }
    public void setPassword(String s) { this.password = s; }
}