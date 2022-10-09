package com.example.chalmerswellness.Controllers.Profile;

import com.example.chalmerswellness.ObjectModels.Exercise;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class ProfileExercisesController extends AnchorPane {

    List<Exercise> exercises;

    public ProfileExercisesController(List<Exercise> exercises){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profileExercisesView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.exercises = exercises;
    }
}
