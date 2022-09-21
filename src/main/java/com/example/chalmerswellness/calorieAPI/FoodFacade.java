package com.example.chalmerswellness.calorieAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FoodFacade {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NutritionAPIConnector apiConnector = new NutritionAPIConnector();

    public Food createFood(String foodName) throws JsonProcessingException {
        Food food = getFood(foodName)[0];

        return food;
    }

    public Food[] getFood(String foodName) throws JsonProcessingException {
        //Try catch here... potentially...
        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(foodName);
        Food[] food = objectMapper.readValue(nutritionJsonString, Food[].class);
        return food;
    }

    public boolean isFoodExisting(String foodName) throws JsonProcessingException {
        Food[] food = getFood(foodName);
        return food.length != 0;
    }
}
