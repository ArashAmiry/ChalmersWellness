package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NutritionViewController extends AnchorPane {

    public NutritionViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

}
