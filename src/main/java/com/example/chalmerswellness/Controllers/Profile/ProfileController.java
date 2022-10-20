package com.example.chalmerswellness.Controllers.Profile;

import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ProfileController extends AnchorPane {

    int userId;
    String name;
    UserService userService;

    @FXML
    Label profileName;

    @FXML
    AnchorPane rootpane;

    @FXML
    AnchorPane calendar;


    public ProfileController(int userId) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profileView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.userId = userId;
        this.userService = UserService.getInstance();
        this.name = userService.getUser(userId).getFirstName() + " " + userService.getUser(userId).getLastName();
        profileName.setText("Hello, I'm " + name);
        calendar.getChildren().add(new CalendarController(rootpane, userId));
    }


}
