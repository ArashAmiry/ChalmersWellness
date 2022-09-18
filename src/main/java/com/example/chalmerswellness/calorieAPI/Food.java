package com.example.chalmerswellness.calorieAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Food {

    public FoodNutritionModel createNutritionModelFor(String query){
        NutritionAPIConnector apiConnector = new NutritionAPIConnector();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FoodNutritionModel.class, new NutritionTypeAdapter());

        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(query);
        FoodNutritionModel[] nutrition = gson.fromJson(nutritionJsonString, FoodNutritionModel[].class);

        return nutrition[0];
    }
}
