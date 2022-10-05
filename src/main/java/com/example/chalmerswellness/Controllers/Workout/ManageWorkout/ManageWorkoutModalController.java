package com.example.chalmerswellness.Controllers.Workout.ManageWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ManageWorkoutModalController {
    private WorkoutModel model;
    public ManageWorkoutModalController(WorkoutModel workoutModel){
        this.model = workoutModel;
        //workoutModel.subscribe(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ManageWorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
