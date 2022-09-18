package com.example.chalmerswellness.calorieAPI;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class NutritionTypeAdapter extends TypeAdapter<FoodNutritionModel> {
    @Override
    public void write(JsonWriter jsonWriter, FoodNutritionModel foodNutritionModel) throws IOException {

    }

    @Override
    public FoodNutritionModel read(JsonReader jsonReader) throws IOException {
        final FoodNutritionModel foodNutritionModel = new FoodNutritionModel();

        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "name":
                    foodNutritionModel.setName(jsonReader.nextString());
                    break;
                case "calories":
                    foodNutritionModel.setCalories(jsonReader.nextDouble());
                    break;
                case "serving_size_g":
                    foodNutritionModel.setServingSize(jsonReader.nextDouble());
                    break;
                case "fat_total_g":
                    foodNutritionModel.setFatTotal(jsonReader.nextDouble());
                    break;
                case "fat_saturated_g":
                    foodNutritionModel.setFatSaturated(jsonReader.nextDouble());
                    break;
                case "protein_g":
                    foodNutritionModel.setProtein(jsonReader.nextDouble());
                    break;
                case "sodium_mg":
                    foodNutritionModel.setSodium(jsonReader.nextDouble());
                    break;
                case "potassium_mg":
                    foodNutritionModel.setPotassium(jsonReader.nextDouble());
                    break;
                case "cholesterol_mg":
                    foodNutritionModel.setCholesterol(jsonReader.nextDouble());
                    break;
                case "carbohydrates_total_g":
                    foodNutritionModel.setCarbohydrates(jsonReader.nextDouble());
                    break;
                case "fiber_g":
                    foodNutritionModel.setFiber(jsonReader.nextDouble());
                    break;
                case "sugar_g":
                    foodNutritionModel.setSugar(jsonReader.nextDouble());
                    break;

                default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return foodNutritionModel;
    }
}
