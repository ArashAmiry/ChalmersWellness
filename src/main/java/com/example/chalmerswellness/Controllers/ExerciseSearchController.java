package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.DataService;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ExercisesApiConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;

public class ExerciseSearchController extends AnchorPane implements Initializable {
    private WorkoutModel model;
    private ExercisesApiConnector apiConnector;
    ObservableList<ExerciseSearchItemController> exercisesList = FXCollections.observableArrayList();
    @FXML public TextField searchField;
    @FXML public Label noResultLabel;
    @FXML public ListView<ExerciseSearchItemController> searchListView;

    public ExerciseSearchController(WorkoutModel model){
        this.model = model;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        apiConnector = new ExercisesApiConnector();

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    void updateSearchResult(List<Exercise> exercises){
        exercisesList.clear();
        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise, model);
            exercisesList.add(exerciseController);
        }
        searchListView.getItems().setAll(exercisesList);
        isNoResult();
    }

    @FXML
    void searchExercise(ActionEvent event){
        //var exercises = apiConnector.searchForExercises(searchField.getText().replaceAll("\\s+","+"));
        var exercises = model.getMyExercises();
        //TODO Add Search method functionality

        updateSearchResult(exercises);
    }


    void isNoResult(){
        if(exercisesList.size()>0){
            noResultLabel.visibleProperty().set(false);
        } else {
            noResultLabel.visibleProperty().set(true);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        //var exercises = apiConnector.getExercises(0);
        var exercises = model.getMyExercises();
        updateSearchResult(exercises);
    }
}
