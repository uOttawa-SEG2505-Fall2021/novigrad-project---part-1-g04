package com.example.servicenovigrad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User(String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }
  
    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }
  
    public String getRole() {
        // String representation of the user (ex: Admin@7cc355be)
        String userToStr = this.toString();
        // Pattern to be matched by previous string
        String pattern = "(Admin|Employee|Client)@[a-z0-9]+";
        // Create Pattern object
        Pattern p = Pattern.compile(pattern);
        // Create Matcher object
        Matcher m = p.matcher(userToStr);
        // Verify if pattern matches
        //noinspection ResultOfMethodCallIgnored
        m.find();
        // Return capture group
        return m.group(1);
    }
}