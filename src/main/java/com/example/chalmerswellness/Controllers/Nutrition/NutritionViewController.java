package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.CalorieIntakeCalculatorController;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Profile;
import com.example.chalmerswellness.calorieAPI.Meal;
import javafx.event.ActionEvent;
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
    private final Profile profile = Profile.getInstance();

    public NutritionViewController() {
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
        progressBarText.setText(profile.calorieGoal() - profile.caloriesConsumed() + " Kcal Left");
        progressBar.setProgress((double) profile.caloriesConsumed() / profile.calorieGoal());
        CalorieIntakeCalculatorController calorieIntakeCalculatorController = new CalorieIntakeCalculatorController();
        calorieIntakeCalculatorController.subscribe(this);
    }

    private void loadNutritionSearchView(Meal meal) {
        rootPane.getChildren().add(new NutritionSearchViewController(rootPane, meal));
        rootPane.setDisable(false);
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
        progressBarText.setText(profile.calorieGoal() - profile.caloriesConsumed() + " Kcal Left");
        progressBar.setProgress((double) profile.caloriesConsumed() / profile.calorieGoal());
    }
}
