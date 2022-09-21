package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.DataService;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Observable;
import com.example.chalmerswellness.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutController extends AnchorPane implements Observer {
    private ObservableList<ExerciseSearchItemController> exercisesList = FXCollections.observableArrayList();
    private DataService dataService = new DataService();
    private WorkoutModel model;
    @FXML public ListView mainContent;
    @FXML public Label noResult;
    @FXML TextField workoutNameField;



    public CreateWorkoutController(WorkoutModel workoutModel){
        this.model = workoutModel;
        workoutModel.subscribe(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateWorkoutView.fxml"));
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
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise, model);
            exercisesList.add(exerciseController);
        }
        mainContent.getItems().setAll(exercisesList);
        isNoResult();
    }

    void isNoResult(){
        if(exercisesList.size() > 0){
            noResult.setVisible(false);
        } else{
            noResult.setVisible(true);
        }
    }


    private Workout createWorkoutObject(ObservableList<ExerciseSearchItemController> exercises){
        String workoutName = workoutNameField.getText().toString();
        List<Exercise> workoutExercises = new ArrayList<>();
        for (var exercise: exercises) {
            workoutExercises.add(exercise.getExercise());
        }
        return new Workout(workoutName, workoutExercises);
    }

    @FXML public void saveWorkout(){
        dataService.insertWorkout(createWorkoutObject(exercisesList));
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        var exercises = model.getAddedExercises();

        updateExerciseList(exercises);
    }
}
