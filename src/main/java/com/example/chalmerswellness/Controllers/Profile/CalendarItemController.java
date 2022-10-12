package com.example.chalmerswellness.Controllers.Profile;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ProfileModel;
import com.example.chalmerswellness.Services.UserServices.UserService;
import com.example.chalmerswellness.Services.WorkoutServices.WorkoutService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarItemController extends Button implements Initializable {
    private ProfileModel profileModel;
    private AnchorPane rootpane;
    private UserService userService;

    int year;
    int month;
    int day;

    int userId;
    String name;

    public CalendarItemController(int year, int month, int day, AnchorPane rootpane, int userId){
        this.profileModel = new ProfileModel();
        this.userService = UserService.getInstance();

        this.rootpane = rootpane;
        this.year = year;
        this.month = month;
        this.day = day;
        this.userId = userId;
        this.name = userService.getUser(userId).getFirstName() + " " + userService.getUser(userId).getLastName();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/calendarItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }


    @FXML
    private void viewProfileExercises(){
        rootpane.getChildren().clear();
        LocalDate date = LocalDate.of(year, month, day);
        List<ExerciseItem> exercises = profileModel.getCompletedExercises(date, userId);
        rootpane.getChildren().add(new ProfileExercisesController(name, exercises, date));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setText(String.valueOf(day));

        LocalDate date = LocalDate.of(year, month, day);
        boolean activity = profileModel.getCompletedExercises(date, userId).size()>0;
        wasActive(activity);
    }

    private void wasActive(boolean wasActive){
        if(wasActive){
            this.setStyle("-fx-background-color: #34a1eb");
        }
    }
}
