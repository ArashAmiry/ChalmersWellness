package com.example.chalmerswellness;

import java.time.LocalDateTime;

public class Profile {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime birthDate;
    private int calorieGoal;
    private int caloriesConsumed;

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }
}
