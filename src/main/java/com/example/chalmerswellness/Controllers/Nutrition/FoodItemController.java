package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.Models.calorieAPI.Food;
import com.example.chalmerswellness.Models.calorieAPI.FoodFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FoodItemController extends AnchorPane{

    Food food;

    @FXML
    Label foodName;
    @FXML
    Label calories;

    public FoodItemController(Food food){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FoodItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.food = food;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        foodName.setText(food.getName());
        calories.setText(food.getCalories() + " kcal");
    }

    @FXML
    private void removeFoodItem(){
        FoodFacade foodFacade = new FoodFacade();
        foodFacade.removeFood(food.getId());
    }

}
