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
    private WorkoutModel workoutModel;
    private ExerciseItem exerciseItem;
    private final AnchorPane anchorPane;
    ObservableList<ExerciseItemSetController> setsList = FXCollections.observableArrayList();
    @FXML private ListView setsListView;
    @FXML private Label addSetsLabel;

    /**
     * This is a constructor for AddSetsController, AddSetsController subscribes to a workoutModel
     * <p>
     * @param workoutModel this is the model that the AddSetsController subscribes to.
     * @param exerciseItem this is the exerciseItem that is about to be modified.
     * @param anchorPane this is the anchorPane which the AddSetsController will be loaded onto.
     */
    public AddSetsController(WorkoutModel workoutModel, ExerciseItem exerciseItem, AnchorPane anchorPane) {
        this.workoutModel = workoutModel;
        workoutModel.subscribe(this);
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

    /**
     * This method sets the title of the view and updates the elements in the list
     * <p>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTitle();
        updateSets();
    }

    /*
    /**
     * This method sets the title of the view and updates the elements in the list
     * <p>
     * @param observable this is the
     */

    @Override
    public void update(Observable observable) {
        this.workoutModel = (WorkoutModel) observable;
        updateSets();
    }

    @FXML private void addSet(){
        ExerciseItemSet exerciseSet = new ExerciseItemSet(exerciseItem.getExerciseItemId(), 0, 0);
        exerciseItem.addSet(exerciseSet);
        saveExerciseItem();
    }

    @FXML private void saveExerciseItem(){
        workoutModel.updateCompletedExercise(exerciseItem);
    }

    private void updateSets(){
        setsList.clear();

        var sets = exerciseItem.getSets();
        for (ExerciseItemSet set: sets) {
            ExerciseItemSetController setsController = new ExerciseItemSetController(workoutModel, exerciseItem, set);
            setsList.add(setsController);
        }

        setsListView.getItems().setAll(setsList);
    }

    private void setTitle(){
        addSetsLabel.textProperty().set("Add Sets To " + exerciseItem.getName());
    }

    @FXML private void close(){
        saveExerciseItem();
        anchorPane.getChildren().remove(this);
    }
    @FXML private void mouseTrap(Event event){
        event.consume();
    }
}
