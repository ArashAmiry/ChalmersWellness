package com.example.chalmerswellness.Controllers.Profile;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ProfileModel;
import com.example.chalmerswellness.Services.WorkoutServices.WorkoutService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CalendarItemController extends Button {
    ProfileModel profileModel = new ProfileModel();
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
        LocalDate date = LocalDate.of(year, month, day);
        List<ExerciseItem> exercises = profileModel.getCompletedExercises(date);
        rootpane.getChildren().add(new ProfileExercisesController(exercises));
    }
}
