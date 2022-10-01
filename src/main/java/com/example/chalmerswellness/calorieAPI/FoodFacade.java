package com.example.chalmerswellness.calorieAPI;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Services.DataService;
import com.example.chalmerswellness.Services.DatabaseConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FoodFacade implements Observable {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NutritionAPIConnector apiConnector = new NutritionAPIConnector();
    private static List<Observer> observers = new ArrayList<>();
    private DataService dataService = new DataService();

    public Food createFood(String foodName) throws JsonProcessingException {
        Food food = getFood(foodName);
        return food;
    }

    private Food getFood(String foodName) {
        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(foodName);
        return new Food(nutritionJsonString);
    }

    public boolean isFoodExisting(String foodName) throws JsonProcessingException {
        try {
            Food food = getFood(foodName);
            return food.getName().equals(foodName);
        }catch (JSONException e){
            return false;
        }
    }

    public void addFoodEaten(String grams, String foodName, Meal meal) throws JsonProcessingException {
        dataService.insertNutrition(this.createFood(grams + "g " + foodName), meal);
        notifyObservers();
    }

    public void removeFood(int foodId){
        DatabaseConnector dataService = new DatabaseConnector();
        dataService.removeNutrition(foodId);
        notifyObservers();
    }

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

    @Override
    public void notifyObservers() {
        for (var observer: observers) {
            observer.update(this);
        }
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }
}
