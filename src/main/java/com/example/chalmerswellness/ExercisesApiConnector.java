package com.example.chalmerswellness;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExercisesApiConnector {
    public ExercisesApiConnector(){
        var t = searchForExercises("Bench");
    }

    public List<ExerciseModel> searchForExercises(String query) {
        List<ExerciseModel> exercises = new ArrayList<>();
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/exercises?name=" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", "Qb+8jJGVQq8tcXv1yCuk2g==UwhHq5RccKnOU6of");
            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(responseStream);
            exercises = jsonToExerciseModel(node);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    private List<ExerciseModel> getExercises(int page){
        List<ExerciseModel> exercises = new ArrayList<>();

        for (int i = page; i<page; i++){

            try {
                URL url = new URL("https://api.api-ninjas.com/v1/exercises?offset="+i);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("X-Api-Key", "Qb+8jJGVQq8tcXv1yCuk2g==UwhHq5RccKnOU6of");
                connection.setRequestProperty("accept", "application/json");

                InputStream responseStream = connection.getInputStream();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(responseStream);

                for (var item: jsonToExerciseModel(node)) {
                    exercises.add(item);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return exercises;
    }

    private List<ExerciseModel> jsonToExerciseModel(JsonNode node) throws JsonProcessingException {
        List<ExerciseModel> exercises = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        for (var item : node) {
            ExerciseModel toValue = mapper.treeToValue(item, ExerciseModel.class);
            exercises.add(toValue);
        }
        return exercises;
    }
}
