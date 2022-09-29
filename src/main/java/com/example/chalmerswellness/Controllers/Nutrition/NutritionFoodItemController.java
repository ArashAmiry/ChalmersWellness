package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.Services.DataService;
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
    private Text foodItemText;
    @FXML
    private Text caloriesPerServing;
    @FXML
    private Text servingSizeGrams;
    @FXML
    private Text totalFatGrams;
    @FXML
    private Text saturatedFatGrams;
    @FXML
    private Text cholesterolMilligrams;
    @FXML
    private Text sodiumMilligrams;
    @FXML
    private Text potassiumMilligrams;
    @FXML
    private Text totalCarbohydrateGrams;
    @FXML
    private Text dietaryFiberGrams;
    @FXML
    private Text totalSugarsGrams;
    @FXML
    private Text totalProteinGrams;
    @FXML
    private TextField foodItemGrams;
    @FXML
    private Button addFoodButton;
    @FXML

    private AnchorPane parentPane;
    @FXML
    private AnchorPane rootPane;


    DataService dataService = new DataService();
    Meal meal;
    FoodFacade foodFacade = new FoodFacade();
    Food food;
    AnchorPane modalPanel;
    NutritionSearchViewController nutritionSearchViewController;


    public NutritionFoodItemController(Food foodItem, AnchorPane pane, Meal meal){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionFoodItemView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        this.meal = meal;
        this.food = foodItem;
        this.parentPane = pane;

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
            foodFacade.addFoodEaten(foodItemGrams.getText(), food.getName(), meal);
            parentPane.getChildren().remove(this);
            parentPane.getChildren().add(new NutritionSearchViewController(parentPane, meal));
        }
        else {
            foodItemGrams.clear();
            foodItemGrams.setPromptText("Please enter a positive integer.");
        }
    }

    private boolean validateAmountGrams(){
        String grams = foodItemGrams.getText();
        return foodFacade.validateAmountOfGrams(grams);
    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        parentPane.getChildren().remove(this);
    }


}
