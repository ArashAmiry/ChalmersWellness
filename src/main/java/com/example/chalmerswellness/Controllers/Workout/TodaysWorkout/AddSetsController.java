package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItemSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSetsController extends AnchorPane implements Initializable, Observer {
    private WorkoutModel model;
    private ExerciseItem exerciseItem;
    private final AnchorPane anchorPane;
    private boolean editable;
    ObservableList<ExerciseItemSetController> setsList = FXCollections.observableArrayList();
    @FXML private ListView setsListView;
    @FXML private Label addSetsLabel;
    @FXML private Button addSetBtn;
    @FXML private Button saveBtn;


    public AddSetsController(WorkoutModel model, ExerciseItem exerciseItem, AnchorPane anchorPane, boolean editable) {
        this.model = model;
        model.subscribe(this);
        this.exerciseItem = exerciseItem;
        this.anchorPane = anchorPane;
        this.editable = editable;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddSetsView.fxml"));
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
        updateSets();
    }

    @Override
    public void update(Observable observable) {
        this.model = (WorkoutModel) observable;
        updateSets();
    }

    private void addSet(){
        ExerciseItemSet exerciseSet = new ExerciseItemSet(0, 0);
        exerciseItem.addSet(exerciseSet);
        saveExerciseItem();
    }

    private void saveExerciseItem(){
        model.updateCompletedExercise(exerciseItem);
    }

    private void updateSets(){
        setsList.clear();

        var sets = exerciseItem.getSets();
        for (ExerciseItemSet set: sets) {
            ExerciseItemSetController setsController = new ExerciseItemSetController(model, exerciseItem, set, editable);
            setsList.add(setsController);
        }

        setsListView.getItems().setAll(setsList);
    }

    @FXML private void close(){
        saveExerciseItem();
        anchorPane.getChildren().remove(this);
    }
    @FXML private void mouseTrap(Event event){
        event.consume();
    }

    private void showEditFunctions(){
        if(editable){
            addSetBtn.setOnMouseClicked(e -> addSet());
            saveBtn.setOnMouseClicked(e -> saveExerciseItem());
            addSetsLabel.textProperty().set("Add Sets To " + exerciseItem.getName());
            buttonVisibility(true);
        } else {
            addSetsLabel.textProperty().set(exerciseItem.getName());
            buttonVisibility(false);
        }
    }

    private void buttonVisibility(boolean isEnabled){
        addSetBtn.setVisible(isEnabled);
        saveBtn.setVisible(isEnabled);
    }
}
