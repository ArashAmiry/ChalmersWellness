package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExerciseItemSetController extends AnchorPane implements Initializable {

    @FXML
    Label exerciseName;
    private ExerciseItemSet exerciseItemSet;

    @FXML private Label weightLabel;
    @FXML private Label repsLabel;

    public ExerciseItemSetController(ExerciseItemSet exerciseItemSet){
        this.exerciseItemSet = exerciseItemSet;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseSet.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        weightLabel.textProperty().set(String.valueOf(exerciseItemSet.getWeight()));
        repsLabel.textProperty().set(String.valueOf(exerciseItemSet.getReps()));
    }
}
