package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItemSet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseItemSetController extends AnchorPane implements Initializable {

    private WorkoutModel model;
    private ExerciseItemSet exerciseItemSet;
    private ExerciseItem exerciseItem;
    private int setNumber;
    private boolean editable;
    @FXML private Label exerciseName;
    @FXML private Label setLabel;
    @FXML private TextField weightField;
    @FXML private TextField repsField;
    @FXML private ImageView removeSetBtn;
    @FXML private Label weightLabel;
    @FXML private Label repsLabel;

    public ExerciseItemSetController(WorkoutModel model, ExerciseItem exerciseItem, ExerciseItemSet exerciseItemSet, boolean editable){
        this.model = model;
        this.exerciseItemSet = exerciseItemSet;
        this.exerciseItem = exerciseItem;
        this.setNumber = exerciseItemSetNumber();
        this.editable = editable;

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
        showEditFunctions();
        setInfo();
    }

    private void removeSet(){
        model.removeSet(exerciseItem, exerciseItemSet);
    }

    private void setInfo(){
        setLabel.textProperty().set("Set " + setNumber);
        weightField.textProperty().set(String.valueOf(exerciseItemSet.getWeight()));
        weightLabel.textProperty().set(String.valueOf(exerciseItemSet.getWeight()));
        repsField.textProperty().set(String.valueOf(exerciseItemSet.getReps()));
        repsLabel.textProperty().set(String.valueOf(exerciseItemSet.getReps()));
    }

    private int exerciseItemSetNumber(){
        return exerciseItem.getSets().indexOf(exerciseItemSet) + 1;
    }

    private void showEditFunctions(){
        if(editable){
            removeSetBtn.setOnMouseClicked(e -> removeSet());
            toggleEditFunctions(true);
            addListeners();
        } else {
            toggleEditFunctions(false);
        }
    }

    private void toggleEditFunctions(boolean enable){
        removeSetBtn.setVisible(enable);
        weightField.setVisible(enable);
        repsField.setVisible(enable);
        weightLabel.setVisible(!enable);
        repsLabel.setVisible(!enable);
    }

    private void addListeners(){
        weightField.textProperty().addListener((observable, oldValue, newValue) -> {
            exerciseItemSet.setWeight(Double.parseDouble(newValue));
        });
        repsField.textProperty().addListener((observable, oldValue, newValue) -> {
            exerciseItemSet.setReps(Integer.parseInt(newValue));
        });
    }
}
