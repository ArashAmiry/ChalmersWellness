package com.example.chalmerswellness.Controllers.Profile;

import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.AddSetsController;
import com.example.chalmerswellness.Models.WorkoutModel;
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

public class ExerciseProfileItemController extends AnchorPane implements Initializable{

    private ExerciseItem exerciseItem;

    @FXML private Label setsAmount;
    @FXML private Button doneBtn;
    @FXML private Label exerciseName;

    private AnchorPane parent;

    public ExerciseProfileItemController(ExerciseItem exerciseItem, AnchorPane parent){
        this.exerciseItem = exerciseItem;
        this.parent = parent;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ExerciseProfileItem.fxml"));
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
    }

    @FXML private void viewSets(){
        AddSetsController setsController = new AddSetsController(new WorkoutModel(), exerciseItem, parent, false);
        parent.getChildren().add(setsController);
    }

    private int getSetCount(){
        return exerciseItem.getSets().size();
    }

    public Exercise getExerciseItem() {
        return exerciseItem;
    }
}
