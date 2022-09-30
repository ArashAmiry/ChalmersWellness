package com.example.chalmerswellness.calorieAPI;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class NutritionTypeAdapter extends TypeAdapter<Food> {
    @Override
    public void write(JsonWriter jsonWriter, Food food) throws IOException {

    }

    @Override
    public Food read(JsonReader jsonReader) throws IOException {
        /*final Food food = new Food();

        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "name":
                    food.setName(jsonReader.nextString());
                    break;
                case "calories":
                    food.setCalories(jsonReader.nextDouble());
                    break;*/
                /*case "serving_size_g":
                    food.setServingSize(jsonReader.nextDouble());
                    break;
                case "fat_total_g":
                    food.setFatTotal(jsonReader.nextDouble());
                    break;
                case "fat_saturated_g":
                    food.setFatSaturated(jsonReader.nextDouble());
                    break;
                case "protein_g":
                    food.setProtein(jsonReader.nextDouble());
                    break;
                case "sodium_mg":
                    food.setSodium(jsonReader.nextDouble());
                    break;
                case "potassium_mg":
                    food.setPotassium(jsonReader.nextDouble());
                    break;
                case "cholesterol_mg":
                    food.setCholesterol(jsonReader.nextDouble());
                    break;
                case "carbohydrates_total_g":
                    food.setCarbohydrates(jsonReader.nextDouble());
                    break;
                case "fiber_g":
                    food.setFiber(jsonReader.nextDouble());
                    break;
                case "sugar_g":
                    food.setSugar(jsonReader.nextDouble());
                    break;*/

                /*default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return food;*/

        Food food = new Food();
        return food;
    }
}
