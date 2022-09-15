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

    @FXML public AnchorPane anchorPaneSearch;

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

    void updateSearchResult(List<ExerciseModel> exercises){
        exercisesList.clear();
        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise);
            exercisesList.add(exerciseController);
        }
    }



    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        //var exercises = apiConnector.getExercises(0);
        //updateSearchResult(exercises);


        ExerciseSearchController esController = new ExerciseSearchController();
        anchorPaneSearch.getChildren().add(esController);
    }
}
