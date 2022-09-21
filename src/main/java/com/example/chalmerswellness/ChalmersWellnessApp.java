package com.example.chalmerswellness;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
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

        Exercise e = new Exercise("name", "type", "muscle", "equipment", "difficulty", "instructions");
        List<Exercise> listOfExercises = new ArrayList<>();
        listOfExercises.add(e);

        Workout workout = new Workout("name1", listOfExercises);
        DataService dataService = new DataService();
        dataService.insertWorkout(workout);

        FoodFacade foodFacade = new FoodFacade();

        Food[] fnm = foodFacade.getFood("apple+fries");
        System.out.println("jhe");
        //dataService.insertNutrition(fnm);


    }

    public static void main(String[] args) {
        launch();
    }
}