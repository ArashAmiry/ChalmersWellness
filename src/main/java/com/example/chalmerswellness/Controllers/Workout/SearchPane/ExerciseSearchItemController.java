package com.example.chalmerswellness.Controllers.Workout.SearchPane;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseSearchItemController extends AnchorPane implements Initializable{

    private final WorkoutModel model;
    private final Exercise exercise;
    @FXML Label exerciseName;

    /**
     * This is a constructor for ExerciseSearchItemController.
     * <p>
     * @param exercise this is the exercise which will be displayed as search result.
     * @param workoutModel this is the model which will be bound to ExerciseSearchItemController.
     */
    public ExerciseSearchItemController(Exercise exercise, WorkoutModel workoutModel){
        this.exercise = exercise;
        this.model = workoutModel;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method set the label to display the exerciseName
     * <p>
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        exerciseName.textProperty().set(exercise.getName());
    }

    /**
     * This method adds an exercise to the active workout or to the created workout
     * <p>
     */
    @FXML public void addToWorkout(){
        model.addExercise(exercise);
    }
}
