package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Models.WorkoutModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSetsController extends AnchorPane implements Initializable {
    private WorkoutModel model;
    private AnchorPane anchorPane;

    @FXML private ListView setsList;

    public AddSetsController(WorkoutModel model, AnchorPane anchorPane) {
        this.model = model;
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

    }

    @FXML private void close(){
        anchorPane.getChildren().remove(this);
    }

    @FXML
    public void mouseTrap(Event event){
        event.consume();
    }

}
