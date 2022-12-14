package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Interfaces.IWorkoutController;
import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
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

public class TodayWorkoutController extends AnchorPane implements Observer, IWorkoutController, Initializable {
    private WorkoutModel model;
    private final AnchorPane mainRoot;
    @FXML private ListView exerciseList;
    @FXML private Label noResult;

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

    public void addExercise(ExerciseItem exercise){
        model.addExerciseToActiveWorkout(exercise);
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

    private void updateExerciseList(List<ExerciseItem> exerciseItems){
        exerciseList.getItems().clear();

        for (ExerciseItem exerciseItem: exerciseItems) {
            exerciseList.getItems().add(new ExerciseItemController(exerciseItem, model, mainRoot));
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
        List<ExerciseItem> todaysExercises = model.getTodayCompletedExercises();
        updateExerciseList(todaysExercises);
    }
}
