package com.example.sustknowledgebase.models;

public class LoginCredentials {
    public String username;
    public String password;

    public LoginCredentials(String username, String password) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
