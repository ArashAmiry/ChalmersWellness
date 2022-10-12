package com.example.chalmerswellness.Controllers.Workout.SearchPane;

import com.example.chalmerswellness.Interfaces.IWorkoutController;
import com.example.chalmerswellness.ObjectModels.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseSearchItemController extends AnchorPane implements Initializable{

    private IWorkoutController workoutController;
    private final Exercise exercise;
    @FXML Label exerciseName;

    public ExerciseSearchItemController(Exercise exercise, IWorkoutController workoutController){
        this.exercise = exercise;
        this.workoutController = workoutController;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        setExerciseName();
    }

    @FXML public void addToWorkout(){
        workoutController.addExercise(exercise);
    }

    private void setExerciseName(){
        exerciseName.textProperty().set(exercise.getName());
    }
}
