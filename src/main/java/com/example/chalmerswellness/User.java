package com.example.chalmerswellness;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int height;
    private LocalDate birthDate;
    private double weight;
    private double weightGoal;

    public User(String username, String firstName, String lastName, String email, int height, LocalDate birthDate, double weight) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
    }


    public String getUsername() {
        return username;
    }

    public double getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}


