package com.example.chalmerswellness.Controllers.Workout;

import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.Workout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutItemController extends AnchorPane implements Initializable{

    private final Workout workoutItem;
    private final WorkoutModel model;
    @FXML
    Label workoutNameLabel;

    public WorkoutItemController(Workout workout, WorkoutModel model){
        this.workoutItem = workout;
        this.model = model;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Workout getWorkout(){
        return workoutItem;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        workoutNameLabel.textProperty().set(workoutItem.getWorkoutName());
    }

    @FXML private void insertToWorkout(){
        model.addExercisesFromWorkout(workoutItem);
    }

    @FXML private void editWorkout(){
        WorkoutController.getWorkoutController().openCreatedWorkout(workoutItem);
        model.removeWorkout(workoutItem);
    }

    @FXML private void deleteWorkout(){
        model.removeWorkout(workoutItem);
    }

}
