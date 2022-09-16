package com.example.chalmerswellness.calorieAPI;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class NutritionTypeAdapter extends TypeAdapter<NutritionModel> {
    @Override
    public void write(JsonWriter jsonWriter, NutritionModel nutritionModel) throws IOException {

    }

    @Override
    public NutritionModel read(JsonReader jsonReader) throws IOException {
        final NutritionModel nutritionModel = new NutritionModel();

        jsonReader.beginObject();
        while(jsonReader.hasNext()){
            switch (jsonReader.nextName()){
                case "name":
                    nutritionModel.setName(jsonReader.nextString());
                    break;
                case "calories":
                    nutritionModel.setCalories(jsonReader.nextDouble());
                    break;
                case "serving_size_g":
                    nutritionModel.setServingSize(jsonReader.nextDouble());
                    break;
                case "fat_total_g":
                    nutritionModel.setFatTotal(jsonReader.nextDouble());
                    break;
                case "fat_saturated_g":
                    nutritionModel.setFatSaturated(jsonReader.nextDouble());
                    break;
                case "protein_g":
                    nutritionModel.setProtein(jsonReader.nextDouble());
                    break;
                case "sodium_mg":
                    nutritionModel.setSodium(jsonReader.nextDouble());
                    break;
                case "potassium_mg":
                    nutritionModel.setPotassium(jsonReader.nextDouble());
                    break;
                case "cholesterol_mg":
                    nutritionModel.setCholesterol(jsonReader.nextDouble());
                    break;
                case "carbohydrates_total_g":
                    nutritionModel.setCarbohydrates(jsonReader.nextDouble());
                    break;
                case "fiber_g":
                    nutritionModel.setFiber(jsonReader.nextDouble());
                    break;
                case "sugar_g":
                    nutritionModel.setSugar(jsonReader.nextDouble());
                    break;

                default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return nutritionModel;
    }
}
