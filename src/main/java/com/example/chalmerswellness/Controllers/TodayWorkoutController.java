package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.Observable;
import com.example.chalmerswellness.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class TodayWorkoutController extends AnchorPane implements Observer {
    private ObservableList<ExerciseItemController> exercisesList = FXCollections.observableArrayList();
    private WorkoutModel model;
    @FXML public ListView exerciseList;
    @FXML public Label noResult;
    AddSetsController addSetsController;

    public TodayWorkoutController(WorkoutModel workoutModel){
        this.model = workoutModel;
        workoutModel.subscribe(this);

        //addSetsController = new AddSetsController(model);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    void updateExerciseList(List<Exercise> exercises){
        exercisesList.clear();

        for (var exercise: exercises) {
            ExerciseItemController exerciseController = new ExerciseItemController(exercise, model, (AnchorPane) this.getParent());
            exercisesList.add(exerciseController);
        }

        exerciseList.getItems().setAll(exercisesList);
        isNoResult();
    }

    void isNoResult(){
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
}
