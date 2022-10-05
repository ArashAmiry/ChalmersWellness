package com.example.chalmerswellness.Controllers.Workout.SearchPane;

import com.example.chalmerswellness.Controllers.Workout.WorkoutStates;
import com.example.chalmerswellness.Models.WorkoutModel;
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

    private final WorkoutModel model;
    private final Exercise exercise;

    @FXML
    Label exerciseName;

    private WorkoutStates workoutState;

    public ExerciseSearchItemController(Exercise exercise, WorkoutModel model, WorkoutStates workoutState){
        this.exercise = exercise;
        this.model = model;

        this.workoutState = workoutState;

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
        model.addExercise(exercise);
    }

    /*@FXML public void addToWorkout(){
        model.addExercise(exercise, workoutState);
    }

     */

/*    @FXML public void removeFromWorkout(){
        model.removeExercise(exercise);
    }*/

    public Exercise getExercise(){
        return this.exercise;
    }

    private void setExerciseName(){
        exerciseName.textProperty().set(exercise.getName());
    }
}
