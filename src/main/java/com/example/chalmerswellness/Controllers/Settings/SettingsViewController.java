package com.example.chalmerswellness.Controllers.Settings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingsViewController extends AnchorPane {

    public SettingsViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SettingsView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
