package com.example.chalmerswellness.calorieAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FoodFacade {

    public Food createNutritionModelFor(String query){
        NutritionAPIConnector apiConnector = new NutritionAPIConnector();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Food.class, new NutritionTypeAdapter());

        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(query);
        Food[] nutrition = gson.fromJson(nutritionJsonString, Food[].class);

        return nutrition[0];
    }

    public Food createFood(String food) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        NutritionAPIConnector apiConnector = new NutritionAPIConnector();
        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(food);

        Food[] foodModel = objectMapper.readValue(nutritionJsonString, Food[].class);
        return foodModel[0];
    }
}
