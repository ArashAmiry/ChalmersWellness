package com.example.chalmerswellness.calorieAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Nutrition {
    public NutritionModel createNutritionModelFor(String query){
        NutritionAPIConnector apiConnector = new NutritionAPIConnector();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(NutritionModel.class, new NutritionTypeAdapter());
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        String nutritionJsonString = apiConnector.getNutritionAsStringFromAPI(query);
        NutritionModel[] nutrition = gson.fromJson(nutritionJsonString, NutritionModel[].class);
        return nutrition[0];
    }
}
