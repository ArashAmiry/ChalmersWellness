package com.example.chalmerswellness;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ExerciseSearchController extends AnchorPane {
    ExercisesApiConnector apiConnector;
    @FXML public TextField searchField;
    @FXML public Label noResultLabel;
    public ExerciseSearchController(){
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

    @FXML
    void searchExercise(ActionEvent event){
        //var exercises = apiConnector.searchForExercises(searchField.getText().replaceAll("\\s+",""));
        //updateSearchResult(exercises);
    }

    void isNoResult(){
        //TODO get ModelExerciseList
        /*
        if(exercisesList.size()>0){
            noResultLabel.visibleProperty().set(false);
        } else {
            noResultLabel.visibleProperty().set(true);
        }
         */
    }

}
