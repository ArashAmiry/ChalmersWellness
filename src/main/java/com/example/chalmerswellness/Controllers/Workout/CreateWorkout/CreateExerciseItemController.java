package com.example.chalmerswellness.Controllers.Workout.CreateWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateExerciseItemController extends AnchorPane implements Initializable{

    private final WorkoutModel model;
    private final Exercise exercise;
    @FXML private TextField setsField;
    @FXML
    Label exerciseName, errorLabel;

    public CreateExerciseItemController(Exercise exercise, WorkoutModel model, AnchorPane anchorPane){
        this.exercise = exercise;
        this.model = model;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateExerciseItem.fxml"));
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
        setName();
        errorLabel.setVisible(false);
    }

    private void setName(){
        exerciseName.textProperty().set(exercise.getName());
    }

    void setSets(){
        this.exercise.setSets(Integer.parseInt(setsField.getText()));
    }

    @FXML
    private void removeFromWorkout(){
        model.removeExercise(exercise);
    }

    public Exercise getExercise() {
        return exercise;
    }
}
