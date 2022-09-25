package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseItemSetController extends AnchorPane implements Initializable {

    @FXML
    Label exerciseName;
    private ExerciseItemSet exerciseItemSet;
    @FXML private Label setLabel;
    @FXML private TextField weightField;
    @FXML private TextField repsField;
    private int setNumber;

    public ExerciseItemSetController(ExerciseItemSet exerciseItemSet, int setNumber){
        this.exerciseItemSet = exerciseItemSet;
        this.setNumber = setNumber;

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
        setLabel.textProperty().set("Set " + setNumber);
        weightField.textProperty().set(String.valueOf(exerciseItemSet.getWeight()));
        repsField.textProperty().set(String.valueOf(exerciseItemSet.getReps()));
    }
}
