package com.example.chalmerswellness;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private int height;
    private LocalDate birthDate;
    private double weight;
    private int calorieGoal;
    private double weightGoal;

    public User(String username, String password, String firstName, String lastName, Gender gender, String email, int height, LocalDate birthDate, double weight) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
    }

    public User(int id, String username, String password, String firstName, String lastName, Gender gender, String email, int height, LocalDate birthDate, double weight, int calorieGoal) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
        this.calorieGoal = calorieGoal;
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

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public String getPassword() {
        return password;
    }
}


