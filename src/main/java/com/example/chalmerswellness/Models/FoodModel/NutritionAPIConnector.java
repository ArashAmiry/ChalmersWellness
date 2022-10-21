package com.example.chalmerswellness.Models.FoodModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NutritionAPIConnector {

    private final String urlString = "https://api.api-ninjas.com/v1/nutrition?query=";

    /**
     * Gets information about food as a JSON string
     * @param query name of food or drink to get information about
     * @return JSON data as string from api
     */
    String getNutritionAsStringFromAPI(String query){
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
        String searchQuery = query.replaceAll("\\s+", "+");
        URL url = new URL(urlString + searchQuery);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("x-api-key", "w654GbcWJOO5z0zkEac5sYcuRKqNjRBZ5BjDqI50");
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.connect();
        return connection;
    }
}
