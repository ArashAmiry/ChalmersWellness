package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Services.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TodayWorkoutController extends AnchorPane implements Observer, Initializable {
    private WorkoutModel model;
    @FXML private ListView exerciseList;
    @FXML private Label noResult;
    private AnchorPane mainRoot;

    public TodayWorkoutController(WorkoutModel workoutModel, AnchorPane mainRoot){
        this.model = workoutModel;
        this.mainRoot = mainRoot;
        workoutModel.subscribe(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateExerciseList();
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        populateExerciseList();
    }

    private void updateExerciseList(List<ExerciseItem> exercises){
        exerciseList.getItems().clear();

        for (var exercise: exercises) {
            exerciseList.getItems().add(new ExerciseItemController(exercise, model, mainRoot));
        }

        if (isExerciseListEmpty()) {
            displayNoResult();
        } else {
            noResult.visibleProperty().set(false);
        }
    }

    private void displayNoResult(){
        noResult.visibleProperty().set(true);
    }

    private boolean isExerciseListEmpty(){
        return exerciseList.getItems().size() <= 0;
    }

    private void populateExerciseList(){
        var todaysExercises = model.getTodayCompletedExercises();
        updateExerciseList(todaysExercises);
    }
}
