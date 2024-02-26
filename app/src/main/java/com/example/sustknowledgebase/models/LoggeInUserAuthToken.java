package com.example.sustknowledgebase.models;

public class LoggeInUserAuthToken {

    private static LoggeInUserAuthToken user = null;
    private String token = null;
    public LoggeInUserAuthToken getInstance() {
        if (user == null) {
            user = new LoggeInUserAuthToken();
        }
        return user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
