package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.FoodModel.FoodFacade;
import com.example.chalmerswellness.Enums.Meal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NutritionViewController extends AnchorPane implements Initializable, Observer {

    @FXML
    private AnchorPane modalPanel;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Text progressBarText;
    @FXML
    private Text breakfastRecommendedCaloriesText;
    @FXML
    private Text lunchRecommendedCaloriesText;
    @FXML
    private Text dinnerRecommendedCaloriesText;
    @FXML
    private Text snackRecommendedCaloriesText;
    @FXML
    private Text carbohydrateAmountText;
    @FXML
    private Text proteinAmountText;
    @FXML
    private Text fatAmountText;

    FoodFacade foodFacade = new FoodFacade();

    public NutritionViewController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NutritionView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        foodFacade.subscribe(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProgressBar();
        setRecommendedCaloriesText();
        setMacroText();
    }

    private void setMacroText() {
        carbohydrateAmountText.setText(String.valueOf(Math.round(foodFacade.getConsumedCarbsToday())));
        proteinAmountText.setText(String.valueOf(Math.round(foodFacade.getConsumedProteinToday())));
        fatAmountText.setText(String.valueOf(Math.round(foodFacade.getConsumedFatToday())));
    }

    private void setRecommendedCaloriesText() {
        int calorieIntake = LoggedInUser.getInstance().getCalorieGoal();
        breakfastRecommendedCaloriesText.setText(Math.round(calorieIntake * 0.25) + " - " + Math.round(calorieIntake * 0.3) + " kcal");
        lunchRecommendedCaloriesText.setText(Math.round(calorieIntake * 0.35) + " - " + Math.round(calorieIntake * 0.4) + " kcal");
        dinnerRecommendedCaloriesText.setText(Math.round(calorieIntake * 0.25) + " - " + Math.round(calorieIntake * 0.3) + " kcal");
        snackRecommendedCaloriesText.setText(Math.round(calorieIntake * 0.05) + " - " + Math.round(calorieIntake * 0.1) + " kcal");
    }

    private void loadNutritionSearchView(Meal meal) {
        rootPane.getChildren().add(new NutritionSearchViewController(rootPane, meal));
        rootPane.setDisable(false);
    }

    private void updateProgressBar(){
        progressBarText.setText(foodFacade.caloriesLeftToday() + " Kcal Left");
        double percentage = foodFacade.caloriesEatenInPercentage();
        progressBar.setProgress(percentage);
    }

    @FXML
    private void openCalorieCalculator() {
        rootPane.getChildren().setAll(new CalorieIntakeCalculatorController());
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

    @Override
    public void update(Observable observable) {
        updateProgressBar();
        setMacroText();
    }
}
