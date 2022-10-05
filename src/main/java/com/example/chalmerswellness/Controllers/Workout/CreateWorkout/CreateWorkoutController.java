package com.example.chalmerswellness.Controllers.Workout.CreateWorkout;

import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.ExerciseItemController;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
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
    private ObservableList<CreateExerciseItemController> exercisesList = FXCollections.observableArrayList();
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

    void updateExerciseList(List<ExerciseItem> exercises){
        exercisesList.clear();

        for (var exercise: exercises) {
            CreateExerciseItemController exerciseController = new CreateExerciseItemController(exercise, model, this);
            exercisesList.add(exerciseController);
        }
        mainContent.getItems().setAll(exercisesList);
    }

    private Workout createWorkoutObject(List<CreateExerciseItemController> exercises){
        String workoutName = workoutNameField.getText();
        List<Exercise> workoutExercises = new ArrayList<>();
        for (var exercise: exercises) {
            workoutExercises.add(exercise.getExerciseItem());
        }
        return new Workout(workoutName, workoutExercises);
    }

    @FXML public void saveWorkout(){
        try {
            saveSets();
            Workout workoutObject = createWorkoutObject(exercisesList);
            model.addWorkout(workoutObject);
            clearWorkoutListView();
        }catch(NumberFormatException nfe){}
    }

    private void saveSets(){
        for (CreateExerciseItemController c : exercisesList){
            try {
                c.setSets();
            }catch (NumberFormatException nfe){
                c.displayErrorLabel();
                throw new NumberFormatException();
            }
        }
    }

    private void clearWorkoutListView(){
        workoutNameField.deleteText(0,workoutNameField.getText().length());
        model.removeAllWorkoutExercises();
        mainContent.getItems().clear();
    }

    @Override
    public void update(Observable observable) {
        model = (WorkoutModel) observable;
        var exercises = model.getAddedWorkoutExercises();
        updateExerciseList(exercises);
    }
}
