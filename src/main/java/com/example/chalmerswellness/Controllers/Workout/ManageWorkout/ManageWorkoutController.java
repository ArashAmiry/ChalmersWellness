package com.example.chalmerswellness.Controllers.Workout.ManageWorkout;

import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.ExerciseItemController;
import com.example.chalmerswellness.Controllers.Workout.WorkoutItemController;
import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.Workout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class ManageWorkoutController extends AnchorPane implements Observer {
    private ObservableList<ExerciseItemController> exercisesList = FXCollections.observableArrayList();
    private ObservableList<WorkoutItemController> workoutsList = FXCollections.observableArrayList();

    private WorkoutModel model;
    @FXML public ListView workoutList;
    @FXML public Label noResult;
    @FXML private AnchorPane mainPane;


    public ManageWorkoutController(WorkoutModel workoutModel){
        this.model = workoutModel;
        workoutModel.subscribe(this);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ManageWorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize(){
        populateWorkoutList();
    }

    void populateWorkoutList(){
        workoutsList.clear();
        for (Workout workout : model.getSavedWorkouts() ){
            workoutsList.add(new WorkoutItemController(workout, model));
        }
        workoutList.getItems().setAll(workoutsList);
    }

    void updateExerciseList(List<ExerciseItem> exerciseItems){
        exercisesList.clear();

        for (ExerciseItem exerciseItem: exerciseItems) {
            ExerciseItemController exerciseController = new ExerciseItemController(exerciseItem, model, this);
            exercisesList.add(exerciseController);
        }
        workoutList.getItems().setAll(exercisesList);
    }

    void updateWorkoutList(List<Workout> workouts){
        workoutsList.clear();
        for (Workout workout : workouts){
            workoutsList.add(new WorkoutItemController(workout, model));
        }
        workoutList.getItems().setAll(workoutsList);
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        updateWorkoutList(model.getSavedWorkouts());
    }
}
