package com.example.chalmerswellness.Controllers.Workout.CreateWorkout;

import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreateExerciseItemController extends AnchorPane implements Initializable{
    private final ExerciseItem exerciseItem;
    @FXML private TextField setsField;
    @FXML
    Label exerciseName, errorLabel;


    private CreateWorkoutController parentController;

    public CreateExerciseItemController(ExerciseItem exercise, CreateWorkoutController createWorkoutController){
        this.exerciseItem = exercise;
        this.parentController = createWorkoutController;

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
        setsField.textProperty().set(String.valueOf(exerciseItem.getSetsCount()));
    }

    private void setName(){
        exerciseName.textProperty().set(exerciseItem.getName());
    }

    void setSets(){
        try {
            this.exerciseItem.setPlannedSetsCount(Integer.parseInt(setsField.getText()));
        }catch (NumberFormatException nfe){
            System.out.println(nfe.getMessage());
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
        parentController.removeExercise(exerciseItem);
    }

    public ExerciseItem getExerciseItem() {
        return exerciseItem;
    }

    @FXML
    private void setsListener(KeyEvent e){
        if(!e.getCharacter().equals(KeyCode.BACK_SPACE.getChar())){
            try {
                setSets();
                hideErrorLabel();
            }catch (NumberFormatException nfe){
                displayErrorLabel();
            }
        }
    }
}
