package com.example.chalmerswellness;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutViewController extends AnchorPane implements Initializable {
    ExercisesApiConnector apiConnector;

    ObservableList<ExerciseSearchItemController> exercisesList = FXCollections.observableArrayList();
    @FXML public ListView<ExerciseSearchItemController> searchListView;


    public WorkoutViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        apiConnector = new ExercisesApiConnector();

        //TODO Searchfield connection
        var exercises = apiConnector.searchForExercises("deadlift");

        for (var exercise: exercises) {
            ExerciseSearchItemController exerciseController = new ExerciseSearchItemController(exercise);
            exercisesList.add(exerciseController);
        }

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        searchListView.getItems().addAll(exercisesList);
    }
}
