package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
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
    private ExerciseItem exerciseItem;
    private final AnchorPane anchorPane;
    @FXML private Label setsAmount;
    @FXML private Button doneBtn;
    @FXML private Label exerciseName;

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
        showDoneButton();
    }

    private void showDoneButton(){
        completedItem();
        if(exerciseItem.getSetsCount() == 0){
            doneBtn.setVisible(true);
        } else {
            if(getSetCount() >= exerciseItem.getSetsCount()){
                doneBtn.setVisible(true);
            }
        }
    }

    private void completedItem(){
        if(exerciseItem.getIsDone()){
            this.setStyle("-fx-background-color: #CBFFB7");
            doneBtn.setVisible(true);
        } else {
            doneBtn.setVisible(false);
        }
    }

    @FXML private void markAsDone(){
        if(!exerciseItem.getIsDone()){
            exerciseItem.setDone(true);
        } else {
            exerciseItem.setDone(false);
        }

        model.updateCompletedExercise(exerciseItem);
    }

    private int getSetCount(){
        return exerciseItem.getSets().size();
    }

    @FXML
    private void removeFromWorkout(){
        model.removeCompletedExercise(exerciseItem);
    }

    @FXML private void openSetsWindow(){
        AddSetsController addSetsController = new AddSetsController(model, exerciseItem, anchorPane, true);
        anchorPane.getChildren().add(addSetsController);
    }

    public Exercise getExerciseItem() {
        return exerciseItem;
    }
}
