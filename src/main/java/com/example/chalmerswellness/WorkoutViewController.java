package com.example.chalmerswellness;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkoutViewController extends AnchorPane implements Initializable {
    ExercisesApiConnector apiConnector;
    ObservableList<ExerciseSearchItemController> exercisesList = FXCollections.observableArrayList();
    @FXML public ListView<ExerciseSearchItemController> searchListView;
    @FXML public TextField searchField;
    @FXML public Label noResultLabel;

    public WorkoutViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        apiConnector = new ExercisesApiConnector();

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void searchExercise(ActionEvent event){
        var exercises = apiConnector.searchForExercises(searchField.getText().replaceAll("\\s+",""));
        updateSearchResult(exercises);
    }

    void updateSearchResult(List<ExerciseModel> exercises){
        exercisesList.clear();

        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise);
            exercisesList.add(exerciseController);
        }
        noResult();
        searchListView.getItems().clear();
        searchListView.getItems().addAll(exercisesList);
    }

    void noResult(){
        if(exercisesList.size()>0){
            noResultLabel.visibleProperty().set(false);
        } else {
            noResultLabel.visibleProperty().set(true);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        var exercises = apiConnector.getExercises(0);
        updateSearchResult(exercises);
    }
}
