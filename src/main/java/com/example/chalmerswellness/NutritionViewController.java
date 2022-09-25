package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Meal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NutritionViewController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane modalPanel;

    public NutritionViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modalPanel.setDisable(true);
    }

    private void loadNutritionSearchView(Meal meal) {
        modalPanel.getChildren().add(new NutritionSearchViewController(modalPanel, meal));
        modalPanel.setDisable(false);
    }

    @FXML
    private void loadBreakfastSearchView(){
        loadNutritionSearchView(Meal.BREAKFAST);
    }

    @FXML
    private void loadLunchSearchView(){
        loadNutritionSearchView(Meal.LUNCH);
    }

    @FXML
    private void loadDinnerSearchView(){
        loadNutritionSearchView(Meal.DINNER);
    }

    @FXML
    private void loadSnackSearchView(){
        loadNutritionSearchView(Meal.SNACK);
    }


}
