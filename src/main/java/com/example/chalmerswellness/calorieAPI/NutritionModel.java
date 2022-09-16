package com.example.chalmerswellness.calorieAPI;

public class NutritionModel {
    private String name;
    private double calories;
    private double servingSize;
    private double fatTotal;
    private double fatSaturated;
    private double protein;
    private double sodium;
    private double potassium;
    private double cholesterol;
    private double carbohydrates;
    private double fiber;
    private double sugar;

    void setCalories(double calories) {
        this.calories = calories;
    }

    void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    void setFatTotal(double fatTotal) {
        this.fatTotal = fatTotal;
    }

    void setFatSaturated(double fatSaturated) {
        this.fatSaturated = fatSaturated;
    }

    void setProtein(double protein) {
        this.protein = protein;
    }

    void setSodium(double sodium) {
        this.sodium = sodium;
    }

    void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    void setFiber(double fiber) {
        this.fiber = fiber;
    }

    void setSugar(double sugar) {
        this.sugar = sugar;
    }

    void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
