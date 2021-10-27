package com.example.servicenovigrad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private String email;
    private String username;
    private String password;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

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
        m.find();
        // Return capture group
        return m.group(1);
    }
}
