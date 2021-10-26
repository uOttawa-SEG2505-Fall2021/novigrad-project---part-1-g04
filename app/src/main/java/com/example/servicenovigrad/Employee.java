package com.example.servicenovigrad;

public class Employee extends User {

    private String email;

    public Employee(String email, String username, String password, char role) {
        super(username, password, 'r');
        this.email = email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return email; }

}
