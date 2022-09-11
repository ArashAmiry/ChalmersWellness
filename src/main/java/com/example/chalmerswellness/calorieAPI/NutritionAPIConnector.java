package com.example.chalmerswellness.calorieAPI;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutritionAPIConnector {

    private final String urlString = "https://api.api-ninjas.com/v1/nutrition?query=pasta";

    public String getNutritionAsStringFromAPI(String query){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        try {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("x-api-key", "w654GbcWJOO5z0zkEac5sYcuRKqNjRBZ5BjDqI50");
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(1000);
            conn.setReadTimeout(1000);
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return String.valueOf(responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String parse(String responseBody) {

        JSONObject album = new JSONObject(responseBody);
        String quote = album.getString("content");
        return quote;
    }
}
