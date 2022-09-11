package com.example.chalmerswellness;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExercisesApiConnector {

    public ExercisesApiConnector(){
        try
        {
            URL url = new URL("https://api.api-ninjas.com/v1/exercises?muscle=biceps");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", "Qb+8jJGVQq8tcXv1yCuk2g==UwhHq5RccKnOU6of");
            connection.setRequestProperty("accept", "application/json");

            InputStream responseStream = connection.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseStream);
            System.out.println(root.path("fact").asText());

            //Deserilize Json
            //Exercise Object
            //Gör lista med Exercises


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
