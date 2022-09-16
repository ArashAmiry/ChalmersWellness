package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Nutrition;
import com.example.chalmerswellness.calorieAPI.NutritionAPIConnector;
import com.example.chalmerswellness.calorieAPI.NutritionModel;
import com.example.chalmerswellness.calorieAPI.NutritionTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class ChalmersWellnessApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChalmersWellnessApp.class.getResource("/fxml/MainView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 192, 108);
        stage.setTitle("Chalmers Wellness");
        stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Nutrition nutrition = new Nutrition();
        NutritionModel pasta = nutrition.createNutritionModelFor("pasta");
    }

    public static void main(String[] args) {
        launch();
    }
}