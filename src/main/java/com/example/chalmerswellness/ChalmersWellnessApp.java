package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DatabaseConnector;
import com.example.chalmerswellness.Services.testImplement.WorkoutServiceTest;
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
        WorkoutServiceTest.createInstance(new WorkoutServiceTest(WorkoutServiceTest.RepositoryType.MockDatabase));

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