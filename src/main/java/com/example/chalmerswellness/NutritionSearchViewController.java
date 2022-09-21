package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Nutrition;
import com.example.chalmerswellness.calorieAPI.NutritionModel;
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
    Nutrition nutrition = new Nutrition();
    NutritionModel nutritionModel = new NutritionModel();


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
        nutritionModel = nutrition.createNutritionModelFor(searchField.getText());
        rootPane.getChildren().setAll(new NutritionFoodItemController(nutritionModel, modalPanel));
    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        modalPanel.getChildren().clear();
        modalPanel.setDisable(true);
    }





}
