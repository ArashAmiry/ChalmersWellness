package com.example.chalmerswellness.Controllers.Profile;

import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.ExerciseItemController;
import com.example.chalmerswellness.Controllers.Workout.TodaysWorkout.TodayWorkoutController;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ProfileExercisesController extends AnchorPane implements Initializable {

    List<ExerciseItem> exercises;

    @FXML
    Label nameLabel;

    @FXML
    Label dateLabel;
    @FXML private ListView exercisesListView;

    String name;
    LocalDate date;

    public ProfileExercisesController(String name, List<ExerciseItem> exercises, LocalDate date){
        this.exercises = exercises;
        this.name = name;
        this.date = date;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profileExercisesView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(name);
        dateLabel.setText("Exercises | " + date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth());
        for (ExerciseItem exerciseItem: exercises) {
            ExerciseProfileItemController controller = new ExerciseProfileItemController(exerciseItem);
            exercisesListView.getItems().add(controller);
        }
    }
}
