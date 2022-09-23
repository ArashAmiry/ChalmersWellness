package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
import com.example.chalmerswellness.calorieAPI.Meal;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NutritionFoodItemController extends AnchorPane implements Initializable {

    @FXML
    Text foodItemText;
    @FXML
    Text caloriesPerServing;
    @FXML
    Text servingSizeGrams;
    @FXML
    Text totalFatGrams;
    @FXML
    Text saturatedFatGrams;
    @FXML
    Text cholesterolMilligrams;
    @FXML
    Text sodiumMilligrams;
    @FXML
    Text potassiumMilligrams;
    @FXML
    Text totalCarbohydrateGrams;
    @FXML
    Text dietaryFiberGrams;
    @FXML
    Text totalSugarsGrams;
    @FXML
    Text totalProteinGrams;
    @FXML
    TextField foodItemGrams;
    @FXML
    Button addFoodButton;
    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane modalPanel;

    DataService dataService = new DataService();

    Meal meal;
    FoodFacade foodFacade = new FoodFacade();
    Food food;

    public NutritionFoodItemController(Food foodItem, AnchorPane pane, Meal meal){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionFoodItemView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.meal = meal;
        food = foodItem;
        modalPanel = pane;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodItemText.textProperty().set(food.getName().substring(0, 1).toUpperCase() + food.getName().substring(1));
        caloriesPerServing.textProperty().set(String.valueOf(food.getCalories()));
        servingSizeGrams.textProperty().set(food.getServingSize() + "g");
        totalFatGrams.textProperty().set(food.getFatTotal() + "g");
        saturatedFatGrams.textProperty().set(food.getFatSaturated() + "g");
        cholesterolMilligrams.textProperty().set(food.getCholesterol() + "mg");
        sodiumMilligrams.textProperty().set(food.getSodium() + "mg");
        potassiumMilligrams.textProperty().set(food.getPotassium() + "mg");
        totalCarbohydrateGrams.textProperty().set(food.getCarbohydrates() + "g");
        dietaryFiberGrams.textProperty().set(food.getFiber() + "g");
        totalSugarsGrams.textProperty().set(food.getSugar() + "g");
        totalProteinGrams.textProperty().set(food.getProtein() + "g");
    }

    @FXML
    private void addFoodEaten(MouseEvent mouseEvent) throws JsonProcessingException {
        if (validateAmountGrams()){
            dataService.insertNutrition(foodFacade.createFood(foodItemGrams.getText() + "g " + food.getName()), meal);
            rootPane.getChildren().setAll(new NutritionSearchViewTwoController(modalPanel, meal));
        }
        else {
            foodItemGrams.textProperty().set("Please enter a positive number");
        }
    }

    private boolean validateAmountGrams(){
        String grams = foodItemGrams.getText();
        if (grams == null) {
            return false;
        }
        try {
            double parsedGrams = Double.parseDouble(grams);
            if (parsedGrams <= 0 ){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        modalPanel.getChildren().clear();
        modalPanel.setDisable(true);
    }


}
