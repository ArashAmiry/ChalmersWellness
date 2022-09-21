package com.example.chalmerswellness.calorieAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FoodFacade {

    public Food createFood(String foodName) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NutritionAPIConnector apiConnector = new NutritionAPIConnector();
        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(foodName);

        Food[] food = objectMapper.readValue(nutritionJsonString, Food[].class);

        assertFoodExistence(food);
        return food[0];
    }

    private void assertFoodExistence(Food[] food) {
        if (food.length == 0) {
            throw new RuntimeException("No food with that name exists in our database.");
        }
    }
}
