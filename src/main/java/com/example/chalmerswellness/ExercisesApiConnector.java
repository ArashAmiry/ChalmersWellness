package com.example.chalmerswellness;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExercisesApiConnector {
    public ExercisesApiConnector(){

    }

    public List<Exercise> searchForExercises(String query) {
        String split = query;

        JsonNode node = exercisesApiCall("name", query);
        List<Exercise> exercises = jsonToExerciseModel(node);

        return exercises;
    }

    public List<Exercise> getExercises(int page){
        List<Exercise> exercises = new ArrayList<>();

        for (int i = 0; i<=page; i++){
           JsonNode node = exercisesApiCall("offset", Integer.toString(i));

            for (var item: jsonToExerciseModel(node)) {
                exercises.add(item);
            }
        }

        return exercises;
    }

    private List<Exercise> jsonToExerciseModel(JsonNode node) {
        List<Exercise> exercises = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            for (var item : node) {
                Exercise toValue = mapper.treeToValue(item, Exercise.class);
                exercises.add(toValue);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return exercises;
    }

    private JsonNode exercisesApiCall(String searchType, String query){
        JsonNode node;
        try{
            URL url = new URL("https://api.api-ninjas.com/v1/exercises?"+ searchType + "=" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", "Qb+8jJGVQq8tcXv1yCuk2g==UwhHq5RccKnOU6of");
            connection.setRequestProperty("accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
            node = mapper.readTree(responseStream);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return node;
    }
}
