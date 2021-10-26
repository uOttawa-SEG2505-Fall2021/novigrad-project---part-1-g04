package com.example.servicenovigrad;

public class User {

    private String email;
    private String username;
    private String password;
    private char role;

    public User(String email, String username, String password, char role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setRole(char role) { this.role = role; }
    
    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public char getRole() { return role; }
}
