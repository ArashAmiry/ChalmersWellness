package com.example.chalmerswellness.Controllers.Nutrition;

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
    private TextField searchField;
    @FXML
    private AnchorPane parentPane;
    private Nutrition nutrition = new Nutrition();
    private NutritionModel nutritionModel = new NutritionModel();


    public NutritionSearchViewController(AnchorPane pane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionSearchView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        parentPane = pane;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @FXML
    private void searchForFoodItem(ActionEvent event) {
        nutritionModel = nutrition.createNutritionModelFor(searchField.getText());
        parentPane.getChildren().remove(this);
        parentPane.getChildren().add(new NutritionFoodItemController(nutritionModel, parentPane));
    }

    @FXML
    private void closeWindow(MouseEvent mouseEvent) {
        parentPane.getChildren().remove(this);
    }

}