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

                default:
                    jsonReader.skipValue();
            }
        }

        jsonReader.endObject();

        return nutritionModel;
    }
}
