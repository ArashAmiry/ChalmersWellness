package com.example.chalmerswellness;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FoodItemController extends AnchorPane {
    public FoodItemController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FoodItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
}
