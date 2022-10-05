package com.example.chalmerswellness.Controllers.Workout.CreateWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
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
    private ExerciseItem exerciseItem;
    @FXML private TextField setsField;
    @FXML
    Label exerciseName, errorLabel;

    public CreateExerciseItemController(ExerciseItem exercise, WorkoutModel model, AnchorPane anchorPane){
        this.exerciseItem = exercise;
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
        hideErrorLabel();
    }

    private void setName(){
        exerciseName.textProperty().set(exerciseItem.getName());
    }

    void setSets(){
        try {
            this.exerciseItem.setSets(Integer.parseInt(setsField.getText()));
        }catch (NumberFormatException nfe){
            throw new NumberFormatException();
        }
    }

    void displayErrorLabel(){
        errorLabel.setVisible(true);
    }

    @FXML
    void hideErrorLabel(){
        errorLabel.setVisible(false);
    }

    @FXML
    private void removeFromWorkout(){
        model.removeExercise(exerciseItem);
    }

    public ExerciseItem getExerciseItem() {
        return exerciseItem;
    }
}