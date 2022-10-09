package com.example.chalmerswellness.Controllers.Profile;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

public class CalendarItemController extends Button {

    public CalendarItemController(int year, int month, int day){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/calendarItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setText(String.valueOf(day));
    }
}
