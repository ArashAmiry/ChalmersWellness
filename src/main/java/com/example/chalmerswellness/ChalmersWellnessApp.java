package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DatabaseConnector;
import com.example.chalmerswellness.Services.DbConnectionService;
import com.example.chalmerswellness.Services.WorkoutServices.DatabaseWorkoutRepository;
import com.example.chalmerswellness.Services.WorkoutServices.WorkoutService;
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