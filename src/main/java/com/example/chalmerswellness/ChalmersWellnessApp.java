package com.example.chalmerswellness;

import com.example.chalmerswellness.Models.Services.DatabaseConnector;
import com.example.chalmerswellness.Models.Services.DbConnectionService;
import com.example.chalmerswellness.Models.Services.FriendServices.DatabaseFriendRepository;
import com.example.chalmerswellness.Models.Services.FriendServices.FriendService;
import com.example.chalmerswellness.Models.Services.NutritionServices.DatabaseNutritionRepository;
import com.example.chalmerswellness.Models.Services.NutritionServices.NutritionService;
import com.example.chalmerswellness.Models.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import com.example.chalmerswellness.Models.Services.WorkoutServices.DatabaseWorkoutRepository;
import com.example.chalmerswellness.Models.Services.WorkoutServices.WorkoutService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChalmersWellnessApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChalmersWellnessApp.class.getResource("/fxml/LoginView.fxml"));
        DatabaseConnector databaseConnector = new DatabaseConnector();

        DbConnectionService.createInstance(true);
        WorkoutService.createInstance(new DatabaseWorkoutRepository());
        UserService.createInstance(new DatabaseUserRepository());
        FriendService.createInstance(new DatabaseFriendRepository());
        NutritionService.createInstance(new DatabaseNutritionRepository());


        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
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