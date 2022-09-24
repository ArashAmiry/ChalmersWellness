package com.example.chalmerswellness.Controllers.Workout.SearchPane;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExerciseSearchController extends AnchorPane implements Initializable {
    private final WorkoutModel model;
    @FXML public TextField searchField;
    @FXML public ListView<ExerciseSearchItemController> searchListView;
    @FXML public Label noResultLabel;

    public ExerciseSearchController(WorkoutModel model){
        this.model = model;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    void updateSearchResult(List<Exercise> exercises){
        searchListView.getItems().clear();
        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise, model);
            searchListView.getItems().add(exerciseController);
        }
        isNoResult();
    }

    //TODO should use model to getexercises instead
    @FXML
    void searchExercise(ActionEvent event){
        var exercises = model.getMyExercises();
        String input = searchField.getText().toLowerCase().replaceAll("\\s+","");
        List<Exercise> searchResult = new ArrayList<>();
        for (var exercise: exercises) {
            if(exercise.getName().toLowerCase().replaceAll("\\s+","").contains(input))
                searchResult.add(exercise);
        }

        updateSearchResult(searchResult);
    }

    void isNoResult(){
        noResultLabel.visibleProperty().set(searchListView.getItems().size() <= 0);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        var exercises = model.getMyExercises();
        updateSearchResult(exercises);
    }
}
