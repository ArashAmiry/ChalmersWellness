package com.example.chalmerswellness;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private int height;
    private LocalDate birthDate;
    private double weight;
    private int calorieGoal;
    private double weightGoal;

    public User(String username, String firstName, String lastName, Gender gender, String email, int height, LocalDate birthDate, double weight) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
    }

    public User(int id, String username, String firstName, String lastName, String email, int height, LocalDate birthDate, double weight) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
    }




    public int getId() {
        return id;
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

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

}


