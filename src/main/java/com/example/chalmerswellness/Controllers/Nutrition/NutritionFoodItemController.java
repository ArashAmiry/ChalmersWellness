package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.calorieAPI.Nutrition;
import com.example.chalmerswellness.calorieAPI.NutritionModel;
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


    Nutrition nutrition = new Nutrition();
    NutritionModel nutritionModel = new NutritionModel();

    public NutritionFoodItemController(NutritionModel foodItem, AnchorPane pane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionFoodItemView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        nutritionModel = foodItem;
        modalPanel = pane;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodItemText.textProperty().set(nutritionModel.getName());
        caloriesPerServing.textProperty().set(String.valueOf(nutritionModel.getCalories()));
        servingSizeGrams.textProperty().set(nutritionModel.getServingSize() + "g");
        totalFatGrams.textProperty().set(nutritionModel.getFatTotal() + "g");
        saturatedFatGrams.textProperty().set(nutritionModel.getFatSaturated() + "g");
        cholesterolMilligrams.textProperty().set(nutritionModel.getCholesterol() + "mg");
        sodiumMilligrams.textProperty().set(nutritionModel.getSodium() + "mg");
        potassiumMilligrams.textProperty().set(nutritionModel.getPotassium() + "mg");
        totalCarbohydrateGrams.textProperty().set(nutritionModel.getCarbohydrates() + "g");
        dietaryFiberGrams.textProperty().set(nutritionModel.getFiber() + "g");
        totalSugarsGrams.textProperty().set(nutritionModel.getSugar() + "g");
        totalProteinGrams.textProperty().set(nutritionModel.getProtein() + "g");
    }

    @FXML
    private void addFoodEaten(MouseEvent mouseEvent) {

    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        modalPanel.getChildren().clear();
        modalPanel.setDisable(true);
    }


}
