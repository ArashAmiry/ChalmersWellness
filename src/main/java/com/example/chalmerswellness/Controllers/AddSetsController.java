package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.Observable;
import com.example.chalmerswellness.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddSetsController extends AnchorPane implements Initializable {
    private WorkoutModel model;
    private ExerciseItem exerciseItem;
    private AnchorPane anchorPane;

    @FXML private Label addSetsLabel;
    @FXML private ListView setsListView;
    ObservableList<ExerciseItemSetController> setsList = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateSets();
        addSetsLabel.textProperty().set("Add Sets To " + exerciseItem.name + exerciseItem.getId());
    }

    @FXML private void addSet(){
        ExerciseItemSet set = new ExerciseItemSet(exerciseItem.getId(), 0, 0);
        model.addSet(set);
        updateSets();
    }

    @FXML private void saveSets(){
        //TODO add code

    }

    private void updateSets(){
        var sets = model.getSets(exerciseItem.getId());
        setsList.clear();
        int setNumber = 1;
        for (var set: sets) {
            ExerciseItemSetController setsController = new ExerciseItemSetController(set, setNumber);
            setsList.add(setsController);
            setNumber++;
        }
        setsListView.getItems().setAll(setsList);
    }

    @FXML private void close(){
        anchorPane.getChildren().remove(this);
    }

    @FXML public void mouseTrap(Event event){
        event.consume();
    }
}
