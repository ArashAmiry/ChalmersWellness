package com.example.chalmerswellness.calorieAPI;

public class NutritionModel {
    String name;
    int calories;
    int servingSize;
    int fatTotal;
    int fatSaturated;
    int protein;
    int sodium;
    int potassium;
    int cholesterol;
    int carbohydratesTotal;
    int fiber;
    int sugar;

    public String getName() { return this.name; }
    void setName(String name) { this.name = name; }

}
