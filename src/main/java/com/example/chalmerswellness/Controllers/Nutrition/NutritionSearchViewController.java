package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
import com.example.chalmerswellness.calorieAPI.Meal;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class NutritionSearchViewController extends ScrollPane implements Observer{

    Meal meal;
    FoodFacade foodFacade = new FoodFacade();
    Food food = new Food();
    DataService dataService = new DataService();
    List<Food> foods;

    @FXML
    Label mealOfDay;
    @FXML
    private TextField searchField;
    @FXML
    private AnchorPane parentPane;
    @FXML
    VBox meals;

    public NutritionSearchViewController(AnchorPane pane, Meal meal){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionSearchView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.setPannable(false);
        this.setHbarPolicy(ScrollBarPolicy.NEVER);
        this.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);

        modalPanel = pane;
        this.meal = meal;
        foodFacade.subscribe(this);
        parentPane = pane;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        populateMealList();
        mealOfDay.setText(String.valueOf(meal));
    }

    private void populateMealList() {
        meals.getChildren().clear();
        foods = dataService.getTodaysNutrition(meal);
        for (Food food: foods) {
            meals.getChildren().add(new FoodItemController(food));
        }
    }

    @FXML
    private void searchForFoodItem(ActionEvent event) {
        parentPane.getChildren().remove(this);
        parentPane.getChildren().add(new NutritionFoodItemController(nutritionModel, parentPane));

        String foodName = searchField.getText();

        try {
            if (foodFacade.isFoodExisting(foodName)){
                food = foodFacade.createFood(foodName);
                rootPane.getChildren().setAll(new NutritionFoodItemController(food, modalPanel, meal));
                this.setVbarPolicy(ScrollBarPolicy.NEVER);
            }
            else {
                searchField.clear();
                searchField.promptTextProperty().set("No food with that name was found.");
            }
        } catch (JsonProcessingException exception){
            searchField.promptTextProperty().set("Server could not process food, please try again");
        }

    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        parentPane.getChildren().remove(this);
    }

    @Override
    public void update(Observable observable) {
        populateMealList();
    }
}
