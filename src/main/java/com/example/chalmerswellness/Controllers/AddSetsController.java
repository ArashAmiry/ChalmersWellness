package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSetsController extends AnchorPane implements Initializable {
    private WorkoutModel model;
    private ExerciseItem exerciseItem;
    private AnchorPane anchorPane;

    @FXML private ListView setsList;
    @FXML private Label addSetsLabel;

    public AddSetsController(WorkoutModel model, ExerciseItem exerciseItem, AnchorPane anchorPane) {
        this.model = model;
        this.exerciseItem = exerciseItem;
        this.anchorPane = anchorPane;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddSetsView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML private void addSet(){
        ExerciseItemSet set = new ExerciseItemSet(exerciseItem.getId(), 0, 0);
        model.addSet(set);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addSetsLabel.textProperty().set("Add Sets To " + exerciseItem.name + exerciseItem.getId());
    }

    @FXML private void close(){
        anchorPane.getChildren().remove(this);
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

}
