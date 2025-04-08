package com.example.iwas.dto;

public class LoginRequest {
    private String empEmail;
    private String password;

    // Getters & Setters
    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
