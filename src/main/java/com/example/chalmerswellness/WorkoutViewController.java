package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WorkoutViewController extends AnchorPane {

    @FXML
    Button workoutBtnNav;

    public WorkoutViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/workoutPanel.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);



        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }



    }

    @FXML public void testFunc(){
        workoutBtnNav.textProperty().set("bruh");
    }


}
