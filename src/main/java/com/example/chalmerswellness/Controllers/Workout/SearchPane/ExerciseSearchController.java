package com.example.chalmerswellness.Controllers.Workout.SearchPane;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
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
    @FXML public TextField searchField;
    @FXML public ListView<ExerciseSearchItemController> searchListView;
    @FXML public Label noResultLabel;

    /**
     * Constructor for ExerciseSearchController
     * <p>
     * @param workoutModel binds workoutModel to this controller
     */
    public ExerciseSearchController(WorkoutModel workoutModel){
        this.model = workoutModel;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * This method searches for all exercises on initialization
     * <p>
     * @param arg0
     * @param arg1
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        searchExercise();
    }

    /**
     * This method updates all the searchListView with the list of exercises
     * <p>
     * @param exercises is the list that will fill the searchListView
     */
    private void updateSearchResult(List<Exercise> exercises){
        searchListView.getItems().clear();
        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise, model);
            searchListView.getItems().add(exerciseController);
        }
        isNoResult();
    }

    /**
     * This method take the input from the searchField then updates the search result.
     * <p>
     */
    @FXML
    private void searchExercise(){
        String input = searchField.getText();
        List<Exercise> searchResult = model.searchExercises(input);
        updateSearchResult(searchResult);
    }

    /**
     * This method displays a label if no results where found.
     * <p>
     */
    private void isNoResult(){
        noResultLabel.visibleProperty().set(searchListView.getItems().size() <= 0);
    }
}
