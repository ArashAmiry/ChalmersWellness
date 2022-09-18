package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodNutritionModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChalmersWellnessApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChalmersWellnessApp.class.getResource("/fxml/MainView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Chalmers Wellness");
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            try {
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        UserNutritionTracker tracker = new UserNutritionTracker();
        Food food = new Food();
        FoodNutritionModel meal = food.createNutritionModelFor("apple");
        tracker.addMeal(1, meal);
        tracker.removeMeal(meal);
    }

    public static void main(String[] args) {
        launch();
    }
}