package com.example.servicenovigrad;

public class Client extends User {

    private String email;

    public Client(String email, String username, String password, char role) {
        super(username, password, 'c');
        this.email = email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getEmail() { return email; }

}
