package com.example.chalmerswellness.Controllers.Workout;

import com.example.chalmerswellness.Controllers.Workout.SearchPane.ExerciseSearchController;
import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.AddSetsController;
import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.TodayWorkoutController;
import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.WorkoutListController;
import com.example.chalmerswellness.Models.WorkoutModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutController extends AnchorPane implements Initializable {

    private WorkoutModel workoutModel;
    @FXML public AnchorPane anchorPaneSearch;
    @FXML public AnchorPane mainContent;
    @FXML private Button addedWorkoutsBtn;
    CreateWorkoutController createWorkoutView;
    TodayWorkoutController todayWorkoutView;
    ExerciseSearchController exerciseSearchController;
    ManageWorkoutController manageWorkoutView;

    public WorkoutController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        workoutModel = new WorkoutModel();

        //TodayWorkoutView
        todayWorkoutView = new TodayWorkoutController(workoutModel, this);

        //SearchPanel
        exerciseSearchController = new ExerciseSearchController(workoutModel);

        //CreateWorkoutView
        createWorkoutView = new CreateWorkoutController(workoutModel);

        //ManageWorkoutView
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
        exerciseSearchController.setState(WorkoutStates.ACTIVEWORKOUT);
        setTabTo(todayWorkoutView);
        addedWorkoutsBtn.setVisible(true);
    }

    @FXML void openCreateWorkoutTab(){
        exerciseSearchController.setState(WorkoutStates.CREATEWORKOUT);
        setTabTo(createWorkoutView);
    }

    @FXML void openManageWorkoutTab(){
        //todayWorkoutView.textProperty().set("ManageWorkout Tab");
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
