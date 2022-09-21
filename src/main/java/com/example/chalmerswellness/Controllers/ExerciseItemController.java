package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseItemController extends AnchorPane implements Initializable{

    private WorkoutModel model;
    private ExerciseItem exercise;
    private AnchorPane anchorPane;

    @FXML
    Label exerciseName;

    public ExerciseItemController(ExerciseItem exercise, WorkoutModel model, AnchorPane anchorPane){
        this.exercise = exercise;
        this.model = model;
        this.anchorPane = anchorPane;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseListItem.fxml"));
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


    @FXML
    private void removeFromWorkout(){
        model.removeExercise(exercise);
    }

    @FXML private void openSetsWindow(){
        AddSetsController addSetsController = new AddSetsController(model, exercise, anchorPane);
        anchorPane.getChildren().add(addSetsController);
    }
}
