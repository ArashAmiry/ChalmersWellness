package com.example.chalmerswellness.Models.FoodModel;

import com.example.chalmerswellness.Enums.Meal;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.Services.NutritionServices.NutritionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FoodFacade implements Observable {
    private final NutritionAPIConnector apiConnector = new NutritionAPIConnector();
    private static List<Observer> observers = new ArrayList<>();
    private final NutritionService nutritionService = NutritionService.getInstance();

    /**
     * Generates a Food item, by parsing data retrieved by the API.
     * @param foodName the name of a food
     * @return Food that is of food in the param
     * @throws JsonProcessingException if processing the json from api does not work as expected
     */
    public Food createFood(String foodName) throws JsonProcessingException {
        Food food = getFood(foodName);
        return food;
    }

    private Food getFood(String foodName) {
        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(foodName);
        return new Food(nutritionJsonString);
    }

    /**
     * Gets amount of consumed protein for today
     * @return amount of protein that was consumed during the day
     */
    public double getConsumedProteinToday() {
        return nutritionService.getTodaysProtein();
    }

    /**
     * Gets amount of consumed carbohydrates for today
     * @return amount of carbohydrates that was consumed during the day
     */
    public double getConsumedCarbsToday() {
        return nutritionService.getTodaysCarbs();
    }

    /**
     * Gets amount of consumed fat for today
     * @return amount of fat that was consumed during the day
     */
    public double getConsumedFatToday() {
        return nutritionService.getTodaysFat();
    }

    /**
     * Checking if the food that was searched for, exists in the API.
     * @param foodName name of a food
     * @return true if food exists in API, otherwise false
     * @throws JsonProcessingException if processing the json from api does not work as expected
     */
    public boolean isFoodExisting(String foodName) throws JsonProcessingException {
        try {
            Food food = getFood(foodName);
            return food.getName().equals(foodName.toLowerCase());
        }catch (JSONException e){
            return false;
        }
    }

    /**
     * Adds food to database
     * @param grams amount of grams eaten
     * @param foodName name of food that was eaten
     * @param meal time of day that the food was eaten
     * @throws JsonProcessingException if processing the json from api does not work as expected
     */
    public void addFoodEaten(String grams, String foodName, Meal meal) throws JsonProcessingException {
        nutritionService.insertNutrition(this.createFood(grams + "g " + foodName), meal);
        notifyObservers();
    }

    /**
     * Removes food from database
     * @param foodId id of food from database that should be removed
     */
    public void removeFood(int foodId){
        nutritionService.removeNutrition(foodId);
        notifyObservers();
    }

    /**
     * Checks whether the inputted amount of grams is a positive integer
     * @param grams amount of grams
     * @return true if amount of grams is a positive integer, otherwise false
     */
    public boolean validateAmountOfGrams(String grams){
        try {
            int parsedGrams = Integer.parseInt(grams);
            if (parsedGrams <= 0 ){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Gets amount of calories that was consumed today
     * @return amount of calories eaten during the day
     */
    public int getTodaysCalories(){
        int todaysCalories = 0;
        for (Meal meal : Meal.values()){
            todaysCalories = caloriesForMealOfDay(todaysCalories, meal);
        }

        return todaysCalories;
    }

    /**
     * Gets amount of grams that is left for today
     * @return amount of calories that is left for the current day
     */
    public int caloriesLeftToday(){
        if (LoggedInUser.getInstance().getCalorieGoal() - this.getTodaysCalories() <= 0){
            return 0;
        }
        else{
            return LoggedInUser.getInstance().getCalorieGoal() - this.getTodaysCalories();
        }
    }

    /**
     * Gets the amount of calories eaten today, formatted in percentage
     * @return amount of calories eaten in percentage for today, based on goal
     */
    public double caloriesEatenInPercentage(){
        if (LoggedInUser.getInstance().getCalorieGoal() == 0){
            return 0;
        }
        else{
            return (double) this.getTodaysCalories() / LoggedInUser.getInstance().getCalorieGoal();
        }
    }

    private int caloriesForMealOfDay(int todaysCalories, Meal meal) {
        List<Food> foods = nutritionService.getTodaysNutrition(meal);
        for (Food food : foods){
            todaysCalories += food.getCalories();
        }
        return todaysCalories;
    }

    /**
     * Notifies all classes that observes FoodFacade
     */
    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update(this);
        }
    }

    /**
     * Allows classes to subscribe to FoodFacade in order to be notified
     * @param observer a class that implements Observer Interface
     */
    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
}
