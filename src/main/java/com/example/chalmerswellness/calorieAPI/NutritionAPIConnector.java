package com.example.chalmerswellness.calorieAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutritionAPIConnector {

    private final String urlString = "https://api.api-ninjas.com/v1/nutrition?query=";

    String getNutritionAsStringFromAPI(String query){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            HttpURLConnection connection = connectToApi(query);

            int responseCode = connection.getResponseCode();

            getResponseContent(responseContent, connection, responseCode);
            return String.valueOf(responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Should not return null, should throw exception and handle it
        return null;
    }

    private void getResponseContent(StringBuffer responseContent, HttpURLConnection connection, int responseCode) throws IOException {
        BufferedReader reader;
        String line;
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            reader.close();
        }
    }

    private HttpURLConnection connectToApi(String query) throws IOException {
        query = query.replaceAll("\\s+", "+");
        URL url = new URL(urlString + query);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("x-api-key", "w654GbcWJOO5z0zkEac5sYcuRKqNjRBZ5BjDqI50");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(2000);
        connection.setReadTimeout(5000);
        connection.connect();
        return connection;
    }
}
