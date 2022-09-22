package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class NutritionSearchViewController extends AnchorPane {

    @FXML
    TextField searchField;
    @FXML
    AnchorPane rootPane;

    @FXML
    AnchorPane modalPanel;
    FoodFacade foodFacade = new FoodFacade();
    Food food = new Food();


    public NutritionSearchViewController(AnchorPane pane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionSearchView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        modalPanel = pane;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void searchForFoodItem(ActionEvent event) {
        String foodName = searchField.getText();

        try {
            if (foodFacade.isFoodExisting(foodName)){
                food = foodFacade.createFood(foodName);
                rootPane.getChildren().setAll(new NutritionFoodItemController(food, modalPanel));
            }
            else {
                searchField.promptTextProperty().set("No food with that name was found.");
            }
        } catch (JsonProcessingException exception){
            searchField.promptTextProperty().set("Server could not process food, please try again");
        }

    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        modalPanel.getChildren().clear();
        modalPanel.setDisable(true);
    }





}
