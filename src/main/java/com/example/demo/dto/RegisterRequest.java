package com.example.demo.dto;
public class RegisterRequest {
    private String fullName, email, password, role;
    public RegisterRequest() {}
    public String getFullName() { return fullName; }
    public void setFullName(String s) { this.fullName = s; }
    public String getEmail() { return email; }
    public void setEmail(String s) { this.email = s; }
    public String getPassword() { return password; }
    public void setPassword(String s) { this.password = s; }
    public String getRole() { return role; }
    public void setRole(String s) { this.role = s; }
}