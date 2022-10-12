package com.example.chalmerswellness.Controllers.Workout;

import com.example.chalmerswellness.Controllers.Workout.CreateWorkout.CreateWorkoutController;
import com.example.chalmerswellness.Controllers.Workout.ManageWorkout.ManageWorkoutController;
import com.example.chalmerswellness.Controllers.Workout.SearchPane.ExerciseSearchController;
import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.TodayWorkoutController;
import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.WorkoutListController;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.Services.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutController extends AnchorPane implements Initializable {

    private WorkoutModel workoutModel;
    private CreateWorkoutController createWorkoutView;
    private TodayWorkoutController todayWorkoutView;
    private ExerciseSearchController exerciseSearchController;
    private ManageWorkoutController manageWorkoutView;
    @FXML private AnchorPane anchorPaneSearch;
    @FXML private AnchorPane mainContent;
    @FXML private Button addedWorkoutsBtn;

    public WorkoutController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        workoutModel = new WorkoutModel();
        todayWorkoutView = new TodayWorkoutController(workoutModel, this);
        exerciseSearchController = new ExerciseSearchController(workoutModel, todayWorkoutView);
        createWorkoutView = new CreateWorkoutController(workoutModel);
        manageWorkoutView = new ManageWorkoutController(workoutModel);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        if(anchorPaneSearch.getChildren().isEmpty()) {
            anchorPaneSearch.getChildren().add(exerciseSearchController);
        }

        addedWorkoutsBtn.setOnAction(event -> showWorkouts());
        openWorkoutTab();
    }

    @FXML void openWorkoutTab(){
        exerciseSearchController.changeController(todayWorkoutView);
        setTabTo(todayWorkoutView);
        addedWorkoutsBtn.setVisible(true);
    }

    @FXML void openCreateWorkoutTab(){
        exerciseSearchController.changeController(createWorkoutView);
        setTabTo(createWorkoutView);
    }

    @FXML void openManageWorkoutTab(){
        setTabTo(manageWorkoutView);
    }

    private void showWorkouts(){
        WorkoutListController workoutListController = new WorkoutListController(workoutModel, this);
        this.getChildren().add(workoutListController);
    }

    private void setTabTo(AnchorPane pane){
        addedWorkoutsBtn.setVisible(false);
        mainContent.getChildren().clear();
        mainContent.getChildren().add(pane);
    }
}
