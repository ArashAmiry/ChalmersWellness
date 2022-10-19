package com.example.chalmerswellness.calorieAPI;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Food {
    private int id;
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
