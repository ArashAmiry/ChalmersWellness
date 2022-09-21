package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.DataService;
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

    private WorkoutModel model;
    private Exercise exercise;

    @FXML
    Label exerciseName;

    public ExerciseSearchItemController(Exercise exercise, WorkoutModel model){
        this.exercise = exercise;
        this.model = model;

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
        exerciseName.textProperty().set(exercise.name);
    }

    @FXML public void addToWorkout(){
        model.addExercise(exercise);


    }

    @FXML public void removeFromWorkout(){
        model.removeExercise(exercise);
    }
}
