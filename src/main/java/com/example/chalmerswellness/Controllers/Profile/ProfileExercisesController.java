package com.example.chalmerswellness.Controllers.Profile;

import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ProfileExercisesController extends AnchorPane {

    List<ExerciseItem> exercises;

    @FXML
    Label nameLabel;

    @FXML
    Label dateLabel;

    String name;
    LocalDate date;

    public ProfileExercisesController(String name, List<ExerciseItem> exercises, LocalDate date){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profileExercisesView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.exercises = exercises;
        this.name = name;
        this.date = date;
        nameLabel.setText(name);
        dateLabel.setText("Exercises | " + date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth());
    }
}
