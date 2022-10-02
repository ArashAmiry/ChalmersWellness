package com.example.chalmerswellness.Controllers.Workout.TodaysWorkout;

import com.example.chalmerswellness.Controllers.Workout.WorkoutItemController;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Workout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WorkoutListController extends AnchorPane implements Initializable {

    private WorkoutModel model;
    private AnchorPane mainContent;
    @FXML private ListView workoutList;
    public WorkoutListController(WorkoutModel model, AnchorPane mainContent){
        this.model = model;
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
        List<Workout> workouts = model.getSavedWorkouts();

        for (Workout workout: workouts) {
            WorkoutItemController workoutController = new WorkoutItemController(workout, model);
            workoutList.getItems().add(workoutController);
        }
    }

    @FXML private void close(){
        mainContent.getChildren().remove(this);
    }

}
