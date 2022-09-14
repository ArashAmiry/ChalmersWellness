package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseSearchItemController extends AnchorPane implements Initializable{
    private ExerciseModel exerciseModel;

    @FXML
    Label exerciseName;

    public ExerciseSearchItemController(ExerciseModel exerciseModel){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSearchItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.exerciseModel = exerciseModel;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        exerciseName.textProperty().set(exerciseModel.name);
    }
}
