package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Food;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FoodItemController extends AnchorPane {

    @FXML
    Label foodName;
    @FXML
    Label calories;

    public FoodItemController(Food food){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FoodItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        foodName.setText(food.getName());
        calories.setText(food.getCalories() + " kcal");
    }
}
