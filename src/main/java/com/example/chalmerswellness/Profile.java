package com.example.chalmerswellness;

import java.time.LocalDateTime;

public class Profile {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime birthDate;
    private Boolean hasCalculatedCalorieIntake = false;
    private int caloriesConsumed;
    private int calorieGoal;
    private double weightGoal;
    private double weight;
    private static final Profile instance = new Profile();
    private Profile() {}

    public static Profile getInstance() {
        return instance;
    }

    public int calorieGoal() {
        return calorieGoal;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public int caloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public Boolean hasCalculatedCalorieIntake() {
        return hasCalculatedCalorieIntake;
    }

    public void setHasCalculatedCalorieIntake(Boolean hasCalculatedCalorieIntake) {
        this.hasCalculatedCalorieIntake = hasCalculatedCalorieIntake;
    }

    public void setWeightGoal(double weightGoal) {
        this.weightGoal = weightGoal;
    }

}
