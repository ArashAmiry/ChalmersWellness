package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.DataService;
import com.example.chalmerswellness.Models.WorkoutModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutController extends AnchorPane implements Initializable {

    private WorkoutModel workoutModel;
    @FXML public AnchorPane anchorPaneSearch;
    @FXML public AnchorPane mainContent;
    CreateWorkoutController createWorkoutView;
    TodayWorkoutController todayWorkoutView;
    ExerciseSearchController esController;
    ManageWorkoutController manageWorkoutView;

    public WorkoutController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        workoutModel = new WorkoutModel();

        //TodayWorkoutView
        todayWorkoutView = new TodayWorkoutController(workoutModel);
        esController = new ExerciseSearchController(workoutModel);



        //CreateWorkoutView
        createWorkoutView = new CreateWorkoutController(workoutModel);
        //ManageWorkoutView
        manageWorkoutView = new ManageWorkoutController(workoutModel);

        //TODO Remove
        DataService dataService = new DataService();


        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        openWorkoutTab();
    }

    @FXML void openWorkoutTab(){
        if(anchorPaneSearch.getChildren().isEmpty()) {
            anchorPaneSearch.getChildren().add(esController);
        }


        setTabTo(todayWorkoutView);
        //todayWorkout.textProperty().set("Workout Tab");
    }

    @FXML void openCreateWorkoutTab(){
        //todayWorkout.textProperty().set("CreateWorkout Tab");
        setTabTo(createWorkoutView);
    }
    @FXML void openManageWorkoutTab(){
        //todayWorkoutView.textProperty().set("ManageWorkout Tab");
        setTabTo(manageWorkoutView);
    }



    private void setTabTo(AnchorPane pane){
        mainContent.getChildren().clear();
        mainContent.getChildren().add(pane);
    }


}
