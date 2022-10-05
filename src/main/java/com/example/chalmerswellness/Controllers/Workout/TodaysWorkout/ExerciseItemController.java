package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExerciseItemController extends AnchorPane implements Initializable{

    private final WorkoutModel model;
    private final ExerciseItem exerciseItem;
    private final AnchorPane anchorPane;

    @FXML private Label setsAmount;
    @FXML private Button doneBtn;

    @FXML private Label exerciseName;

    // TODO Substitute plannedSets
    //private int plannedSets = 3;

    public ExerciseItemController(ExerciseItem exerciseItem, WorkoutModel model, AnchorPane anchorPane){
        this.exerciseItem = exerciseItem;
        this.model = model;
        this.anchorPane = anchorPane;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        setInfo();
    }

    private void setInfo(){
        exerciseName.textProperty().set(exerciseItem.getName());
        setsAmount.textProperty().set("Sets " + getSetCount());

        if(isExerciseDone()){
            indicateDone();
        } else {
            doneBtn.setVisible(false);
        }
    }

    @FXML private void markAsDone(){
        exerciseItem.setDone(true);
        model.updateCompletedExercise(exerciseItem);
    }

    private void indicateDone(){
        this.setStyle("-fx-background-color: #CBFFB7");
        doneBtn.setVisible(true);
    }

    private boolean isExerciseDone(){
        if(exerciseItem.getSetsCount() == 0){
            return false;
        }

        return getSetCount() >= exerciseItem.getSetsCount();
    }

    private int getSetCount(){
        return model.getSets(exerciseItem.getExerciseItemId()).size();
    }

    @FXML
    private void removeFromWorkout(){
        model.removeExercise(exerciseItem);
    }

    @FXML private void openSetsWindow(){
        AddSetsController addSetsController = new AddSetsController(model, exerciseItem, anchorPane);
        anchorPane.getChildren().add(addSetsController);
    }

    public Exercise getExerciseItem() {
        return exerciseItem;
    }
}
