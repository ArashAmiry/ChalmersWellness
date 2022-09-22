package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Observable;
import com.example.chalmerswellness.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<ExerciseItemController> exercisesList = FXCollections.observableArrayList();
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

    private void updateExerciseList(List<ExerciseItem> exercises){
        exercisesList.clear();

        for (var exercise: exercises) {
            exercisesList.add(new ExerciseItemController(exercise, model, this));
        }

        exerciseList.getItems().setAll(exercisesList);
        isNoResult();
    }

    private void isNoResult(){
        if(exercisesList.size() > 0){
            noResult.setVisible(false);
        } else{
            noResult.setVisible(true);
        }
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        var exercises = model.getAddedExercises();
        updateExerciseList(exercises);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.addExerciseDb();
    }
}
