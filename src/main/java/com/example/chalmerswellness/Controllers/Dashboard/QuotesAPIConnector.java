package com.example.chalmerswellness.Controllers.Dashboard;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuotesAPIConnector {

    public String getRandomQuoteAsStringFromAPI(String query){
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();
        try {
            String urlString = "https://api.quotable.io/random?maxLength=100&tags=";
            URL url = new URL(urlString + query);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return getQuote(String.valueOf(responseContent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private static String getQuote(String responseBody) {
        JSONObject album = new JSONObject(responseBody);
        return album.getString("content");
    }

}
