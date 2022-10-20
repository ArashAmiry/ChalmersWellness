package com.example.chalmerswellness.Controllers.Workout.CreateWorkout;

import com.example.chalmerswellness.Interfaces.IWorkoutController;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.Workout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class CreateWorkoutController extends AnchorPane implements IWorkoutController {
    private ObservableList<CreateExerciseItemController> exercisesList = FXCollections.observableArrayList();
    private WorkoutModel model;
    @FXML public ListView mainContent;
    @FXML TextField workoutNameField;

    private Workout workout;

    public CreateWorkoutController(WorkoutModel workoutModel){
        this.model = workoutModel;
        workout = new Workout();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CreateWorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setWorkoutName(String name){
        workoutNameField.setText(name);
    }

    @Override
    public void addExercise(ExerciseItem exercise){
        workout.addExercise(exercise);
        update();
    }

    public void removeExercise(ExerciseItem exercise){
        workout.removeExercise(exercise);
        update();
    }

    public void clearExercises(){
        for (var exercise : workout.getExercises()) {
            removeExercise(exercise);
        }
        update();
    }

    void updateExerciseList(List<ExerciseItem> exercises){
        exercisesList.clear();

        for (var exercise: exercises) {
            CreateExerciseItemController exerciseController = new CreateExerciseItemController(exercise, this);
            exercisesList.add(exerciseController);
        }
        mainContent.getItems().setAll(exercisesList);
    }

    @FXML public void saveWorkout(){
        try {
            saveSets();
            model.addWorkout(new Workout(workoutNameField.getText(), workout.getExercises()));
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
        workout = new Workout();
        mainContent.getItems().clear();
    }

    private void update() {
        var exercises = workout.getExercises();
        updateExerciseList(exercises);
    }

}
