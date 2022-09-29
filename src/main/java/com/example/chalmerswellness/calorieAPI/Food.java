package com.example.chalmerswellness.calorieAPI;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public Food(String jsonData){
        populateFieldsWithMapper(jsonData);

    }

    private void populateFieldsWithMapper(String jsonData) throws JSONException {

            JSONArray jsonArr = new JSONArray(jsonData);

            JSONObject jsonObj = jsonArr.getJSONObject(0);
            this.name = jsonObj.getString("name");
            this.calories = jsonObj.getDouble("calories");
            this.servingSize = jsonObj.getDouble("serving_size_g");
            this.fatTotal = jsonObj.getDouble("fat_total_g");
            this.fatSaturated = jsonObj.getDouble("fat_saturated_g");
            this.protein = jsonObj.getDouble("protein_g");
            this.sodium = jsonObj.getDouble("sodium_mg");
            this.potassium = jsonObj.getDouble("potassium_mg");
            this.cholesterol = jsonObj.getDouble("cholesterol_mg");
            this.carbohydrates = jsonObj.getDouble("carbohydrates_total_g");
            this.fiber = jsonObj.getDouble("fiber_g");
            this.sugar = jsonObj.getDouble("sugar_g");
    }

    public Food(int id, String foodName, double calories){
        this.id = id;
        this.name = foodName;
        this.calories = calories;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getServingSize() {
        return servingSize;
    }

    public double getFatTotal() {
        return fatTotal;
    }

    public double getFatSaturated() {
        return fatSaturated;
    }

    public double getProtein() {
        return protein;
    }

    public double getSodium() {
        return sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFiber() {
        return fiber;
    }

    public double getSugar() {
        return sugar;
    }
}
