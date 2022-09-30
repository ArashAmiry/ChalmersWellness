package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
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

    public TodayWorkoutController(WorkoutModel workoutModel){
        this.model = workoutModel;
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

    private void updateExerciseList(List<Exercise> exercises){
        exerciseList.getItems().clear();

        for (var exercise: exercises) {
            exerciseList.getItems().add(new ExerciseItemController(exercise, model, this));
        }

        displayNoResult(isExerciseListEmpty());
    }

    private void displayNoResult(boolean isNoResult){
        noResult.visibleProperty().set(isNoResult);
    }

    private boolean isExerciseListEmpty(){
        return exerciseList.getItems().size() <= 0;
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        var exercises = model.getAddedExercises();
        updateExerciseList(exercises);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.getTodaysExercises();
    }
}
