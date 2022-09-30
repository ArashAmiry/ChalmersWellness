package com.example.chalmerswellness.Controllers.Workout;

import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.ExerciseItemController;
import com.example.chalmerswellness.Services.DataService;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateWorkoutController extends AnchorPane implements Observer {
    private ObservableList<ExerciseItemController> exercisesList = FXCollections.observableArrayList();
    private DataService dataService = new DataService();
    private WorkoutModel model;
    @FXML public ListView mainContent;
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
            ExerciseItemController exerciseController = new ExerciseItemController(exercise, model, this);
            exercisesList.add(exerciseController);
        }
        mainContent.getItems().setAll(exercisesList);
    }

    private Workout createWorkoutObject(List<ExerciseItemController> exercises){
        String workoutName = workoutNameField.getText();
        List<Exercise> workoutExercises = new ArrayList<>();
        for (var exercise: exercises) {
            workoutExercises.add(exercise.getExercise());
        }
        return new Workout(workoutName, workoutExercises);
    }

    @FXML public void saveWorkout(){
        Workout workoutObject = createWorkoutObject(exercisesList);
        model.addWorkout(workoutObject);
        clearWorkoutListView();
    }

    private void clearWorkoutListView(){
        workoutNameField.deleteText(0,workoutNameField.getText().length());
        model.removeAllExercises();
        mainContent.getItems().clear();
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        var exercises = model.getAddedWorkoutExercises();

        updateExerciseList(exercises);
    }
}
