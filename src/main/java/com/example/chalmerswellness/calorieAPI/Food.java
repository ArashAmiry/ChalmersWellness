package com.example.chalmerswellness.calorieAPI;

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

    /**
     *
     * @param jsonData the JSON data retrieved by API
     */
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

    /**
     *
     * @param id autogenerated by database
     * @param foodName the name of the food
     * @param calories the amount of calories the food has
     */
     public Food(int id, String foodName, double calories){
        this.id = id;
        this.name = foodName;
        this.calories = calories;
    }

    /**
     *
     * @return the id of the food that it has in database
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return name of food
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return calories of food
     */
    public double getCalories() {
        return calories;
    }

    /**
     *
     * @return the foods serving size
     */
    public double getServingSize() {
        return servingSize;
    }

    /**
     *
     * @return total fat of food
     */
    public double getFatTotal() {
        return fatTotal;
    }

    /**
     *
     * @return saturated fat of food
     */
    public double getFatSaturated() {
        return fatSaturated;
    }

    /**
     *
     * @return amount of protein
     */
    public double getProtein() {
        return protein;
    }

    /**
     *
     * @return amount of sodium
     */
    public double getSodium() {
        return sodium;
    }

    /**
     *
     * @return amount of potassium
     */
    public double getPotassium() {
        return potassium;
    }

    /**
     *
     * @return amount of cholesterol
     */
    public double getCholesterol() {
        return cholesterol;
    }

    /**
     *
     * @return amount of carbohydrates
     */
    public double getCarbohydrates() {
        return carbohydrates;
    }

    /**
     *
     * @return amount of fiber
     */
    public double getFiber() {
        return fiber;
    }

    /**
     *
     * @return amount of sugar
     */
    public double getSugar() {
        return sugar;
    }
}
