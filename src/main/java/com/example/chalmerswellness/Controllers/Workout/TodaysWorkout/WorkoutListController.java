package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Controllers.Workout.WorkoutItemController;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import com.example.chalmerswellness.Models.ObjectModels.Workout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkoutListController extends AnchorPane implements Initializable, Observer {

    private final WorkoutModel model;
    private final AnchorPane mainContent;
    @FXML private ListView workoutList;
    public WorkoutListController(WorkoutModel model, AnchorPane mainContent){
        this.model = model;
        model.subscribe(this);
        this.mainContent = mainContent;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
            IOException exception) {
                throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshWorkoutlist();
    }

    private void refreshWorkoutlist(){
        workoutList.getItems().clear();
        List<Workout> workouts = model.getSavedWorkouts();
        for (Workout workout: workouts) {
            WorkoutItemController workoutController = new WorkoutItemController(workout, model);
            workoutList.getItems().add(workoutController);
        }
    }

    @FXML private void close(){
        mainContent.getChildren().remove(this);
    }

    @Override
    public void update(Observable observable) {
        refreshWorkoutlist();
    }
}
