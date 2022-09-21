package com.example.chalmerswellness.calorieAPI;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Food {
    private int id;
    private String name;
    private double calories;
    @JsonAlias("serving_size_g")
    private double servingSize;
    @JsonAlias("fat_total_g")
    private double fatTotal;
    @JsonAlias("fat_saturated_g")
    private double fatSaturated;
    @JsonAlias("protein_g")
    private double protein;
    @JsonAlias("sodium_mg")
    private double sodium;
    @JsonAlias("potassium_mg")
    private double potassium;
    @JsonAlias("cholesterol_mg")
    private double cholesterol;
    @JsonAlias("carbohydrates_total_g")
    private double carbohydrates;
    @JsonAlias("fiber_g")
    private double fiber;
    @JsonAlias("sugar_g")
    private double sugar;

    public Food(){

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getServingSize() {
        return servingSize;
    }

    public void setServingSize(double servingSize) {
        this.servingSize = servingSize;
    }

    public double getFatTotal() {
        return fatTotal;
    }

    public void setFatTotal(double fatTotal) {
        this.fatTotal = fatTotal;
    }

    public double getFatSaturated() {
        return fatSaturated;
    }

    public void setFatSaturated(double fatSaturated) {
        this.fatSaturated = fatSaturated;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getFiber() {
        return fiber;
    }

    public void setFiber(double fiber) {
        this.fiber = fiber;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }
}
