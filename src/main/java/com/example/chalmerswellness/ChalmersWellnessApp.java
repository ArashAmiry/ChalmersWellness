package com.example.chalmerswellness;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Services.DataService;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
import com.example.chalmerswellness.calorieAPI.Meal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    }

    public static void main(String[] args) {
        launch();
    }
}