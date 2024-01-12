package com.example.projekt2;

public class User {
    private String username;
    private static User instance = new User();
    private User() {}

    public static User getInstance() {
        return instance;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
