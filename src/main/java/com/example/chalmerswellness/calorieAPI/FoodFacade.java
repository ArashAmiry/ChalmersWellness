package com.example.chalmerswellness.calorieAPI;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Services.DataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class FoodFacade implements Observable {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NutritionAPIConnector apiConnector = new NutritionAPIConnector();
    private static List<Observer> observers = new ArrayList<>();

    public Food createFood(String foodName) throws JsonProcessingException {
        Food food = getFood(foodName)[0];
        return food;
    }

    private Food[] getFood(String foodName) throws JsonProcessingException {
        //Try catch here... potentially...
        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(foodName);
        Food[] food = objectMapper.readValue(nutritionJsonString, Food[].class);
        return food;
    }

    public boolean isFoodExisting(String foodName) throws JsonProcessingException {
        Food[] food = getFood(foodName);
        return food.length == 1;
    }

    public void removeFood(int foodId){
        DataService dataService = new DataService();
        dataService.removeNutrition(foodId);
        notifyObservers();
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
