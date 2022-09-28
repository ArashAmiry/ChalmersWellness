package com.example.chalmerswellness;

public class User {
    private String username;
    private int id;

    private final static User instance = new User();

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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
