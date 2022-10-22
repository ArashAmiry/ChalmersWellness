package com.example.chalmerswellness.Controllers.Dashboard;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class QuotesAPIConnector {
    private static final int HTTP_OK = 200;
    private static final int TIMEOUT = 1000;

    public String getRandomQuoteAsStringFromAPI(final String query){
        BufferedReader reader;
        String line;
        final StringBuilder responseContent = new StringBuilder();
        try {
            final String urlString = "https://api.quotable.io/random?maxLength=100&tags=";
            final URL url = new URL(urlString + query);

            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.connect();

            final int responseCode = connection.getResponseCode();

            if (responseCode == HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getQuote(String.valueOf(responseContent));
    }
    private static String getQuote(final String responseBody) {
        final JSONObject album = new JSONObject(responseBody);
        return album.getString("content");
    }

}
