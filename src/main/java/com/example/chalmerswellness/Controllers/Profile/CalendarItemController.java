package com.example.chalmerswellness.Controllers.Profile;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ProfileModel;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarItemController extends Button implements Initializable {
    private ProfileModel profileModel;
    private AnchorPane rootpane;
    private UserService userService;

    private LocalDate date;


    int userId;
    String name;

    public CalendarItemController(LocalDate date, AnchorPane rootpane, int userId){
        this.date = date;

        this.profileModel = new ProfileModel();
        this.userService = UserService.getInstance();
        this.rootpane = rootpane;
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
        List<ExerciseItem> exercises = profileModel.getCompletedExercises(date, userId);
        rootpane.getChildren().add(new ProfileExercisesController(name, exercises, date));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setText(String.valueOf(date.getDayOfMonth()));
        indicateActive();
    }

    private void indicateActive(){
        if(profileModel.getCompletedExercises(date, userId).size()>0){
            this.setStyle("-fx-background-color: #34a1eb");
        }
    }
}
