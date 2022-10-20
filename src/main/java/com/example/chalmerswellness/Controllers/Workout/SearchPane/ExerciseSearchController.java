package com.example.chalmerswellness.Controllers.Workout.SearchPane;

import com.example.chalmerswellness.Interfaces.IWorkoutController;
import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ExerciseSearchController extends AnchorPane implements Initializable {
    private final WorkoutModel model;
    private IWorkoutController workoutController;
    @FXML public TextField searchField;
    @FXML public ListView<ExerciseSearchItemController> searchListView;
    @FXML public Label noResultLabel;

    public ExerciseSearchController(WorkoutModel model, IWorkoutController workoutController){
        this.model = model;
        this.workoutController = workoutController;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void changeController(IWorkoutController workoutController){
        this.workoutController = workoutController;
        searchExercise();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        searchExercise();
    }

    private void updateSearchResult(List<Exercise> exercises){
        searchListView.getItems().clear();
        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise, workoutController);
            searchListView.getItems().add(exerciseController);
        }
        isNoResult();
    }

    @FXML
    void searchExercise(){
        String input = searchField.getText();
        var searchResult = model.searchExercises(input);
        updateSearchResult(searchResult);
    }

    void isNoResult(){
        noResultLabel.visibleProperty().set(searchListView.getItems().size() <= 0);
    }
}
