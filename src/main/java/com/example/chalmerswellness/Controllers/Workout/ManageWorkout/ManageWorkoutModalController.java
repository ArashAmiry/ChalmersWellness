package com.example.chalmerswellness.Controllers.Workout.ManageWorkout;

import com.example.chalmerswellness.Controllers.Workout.CreateWorkout.CreateExerciseItemController;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageWorkoutModalController extends AnchorPane {
    private ObservableList<CreateExerciseItemController> exercisesList = FXCollections.observableArrayList();

    private WorkoutModel model;
    private Workout workout;

    @FXML Button closeButton, saveButton;
    @FXML ListView exerciseListView;
    @FXML Label workoutNameLabel;
    public ManageWorkoutModalController(WorkoutModel workoutModel, Workout workout){
        this.model = workoutModel;
        this.workout = workout;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ManageWorkoutModal.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        workoutNameLabel.textProperty().set(workout.getWorkoutName());

    }
}

