package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    ObservableList<ExerciseItemSetController> setsList = FXCollections.observableArrayList();
    @FXML private ListView setsListView;
    @FXML private Label addSetsLabel;

    public AddSetsController(WorkoutModel model, ExerciseItem exerciseItem, AnchorPane anchorPane) {
        this.model = model;
        model.subscribe(this);

        this.exerciseItem = exerciseItem;
        this.anchorPane = anchorPane;
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
        setTitle();
        updateSets();
    }

    @Override
    public void update(Observable observable) {
        this.model = (WorkoutModel) observable;
        updateSets();
    }

    @FXML private void addSet(){
        //saveSets();

        ExerciseItemSet exerciseSet = new ExerciseItemSet(exerciseItem.getExerciseItemId(), 0, 0);
        exerciseItem.addSet(exerciseSet);

        //New Version
        model.updateCompletedExercise(exerciseItem);

        //Old Version
        //model.addSet(exerciseItem);
    }

    @FXML private void saveSets(){
        for (var item: setsList) {
            item.setValues();
        }

        //model.saveSets(exerciseItem.getExerciseItemId());
        model.updateCompletedExercise(exerciseItem);
    }

    private void updateSets(){
        setsList.clear();


        //Old Version
        //var sets = model.getSets(exerciseItem.getExerciseItemId());

        //New Version
        var sets = exerciseItem.getSets();
        int setNumber = 1;

        for (var set: sets) {
            ExerciseItemSetController setsController = new ExerciseItemSetController(model, set, setNumber);
            setsList.add(setsController);
            setNumber++;
        }

        setsListView.getItems().setAll(setsList);
    }

    private void setTitle(){
        addSetsLabel.textProperty().set("Add Sets To " + exerciseItem.getName());
    }

    @FXML private void close(){
        saveSets();
        anchorPane.getChildren().remove(this);
    }
    @FXML private void mouseTrap(Event event){
        event.consume();
    }
}
