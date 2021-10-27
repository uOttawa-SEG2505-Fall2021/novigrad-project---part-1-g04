package com.example.servicenovigrad;

public class User {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private char role;

    public User(String email, String username, String password, String firstName, String lastName, char role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public User(String username, String password, char role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstName(String firstName) {this.firstName = firstName; }

    public void setLastName(String lastName) {this.lastName = lastName; }

    public void setRole(char role) { this.role = role; }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public String getFirstName() {return firstName; }

    public String getLastName() {return lastName; }

    public char getRole() { return role; }
}
