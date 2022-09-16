package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WorkoutController extends AnchorPane implements Initializable {

    @FXML public AnchorPane anchorPaneSearch;
    @FXML public AnchorPane mainContent;


    public WorkoutController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/WorkoutView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        //TodayWorkoutView
        //CreateWorkoutView
        //ManageWorkoutView

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        ExerciseSearchController esController = new ExerciseSearchController();
        anchorPaneSearch.getChildren().add(esController);
    }

    @FXML void openWorkoutTab(){
        //setTabTo(workoutTab);
        //todayWorkout.textProperty().set("Workout Tab");
    }

    @FXML void openCreateWorkoutTab(){
        //todayWorkout.textProperty().set("CreateWorkout Tab");
        //setTabTo(createWorkoutTab);
    }
    @FXML void openMangeWorkoutTab(){
        //todayWorkout.textProperty().set("ManageWorkout Tab");
        //setTabTo(manageWorkoutTab);
    }


    /*
    private void setTabTo(AnchorPane pane){
        mainContent.getChildren().clear();
        mainContent.getChildren().add(pane);
    }*/


}
