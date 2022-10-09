package com.example.chalmerswellness.Controllers.Profile;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.Services.WorkoutService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CalendarItemController extends Button {

    WorkoutService workoutService = new WorkoutService();
    AnchorPane rootpane;

    int year;
    int month;
    int day;


    public CalendarItemController(int year, int month, int day, AnchorPane rootpane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/calendarItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setText(String.valueOf(day));
        this.rootpane = rootpane;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @FXML
    private void viewProfileExercises(){
        rootpane.getChildren().clear();
        String date = String.valueOf(year + "-" + month + "-" + day);
        List<Exercise> exercises = workoutService.getCompletedExercises(Date.valueOf(date));
        rootpane.getChildren().add(new ProfileExercisesController(exercises));
    }
}
