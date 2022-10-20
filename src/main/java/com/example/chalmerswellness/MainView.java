package com.example.chalmerswellness;

import com.example.chalmerswellness.Controllers.Nutrition.CalorieIntakeCalculatorController;
import com.example.chalmerswellness.Controllers.Dashboard.DashboardViewController;
import com.example.chalmerswellness.Controllers.Friends.FriendsViewController;
import com.example.chalmerswellness.Controllers.Nutrition.NutritionViewController;
import com.example.chalmerswellness.Controllers.Settings.SettingsViewController;
import com.example.chalmerswellness.Controllers.Workout.WorkoutController;
import com.example.chalmerswellness.Models.LoggedInUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MainView extends AnchorPane {
    @FXML AnchorPane contentRootAnchorPane;
    @FXML AnchorPane navigationAnchorPane;
    @FXML AnchorPane templateRootAnchorPane;
    @FXML Button workoutBtn;
    @FXML Text firstNameText;

    WorkoutController workoutView = WorkoutController.getInstance();
    NutritionViewController nutritionViewController = new NutritionViewController();
    CalorieIntakeCalculatorController calorieIntakeCalculatorController = new CalorieIntakeCalculatorController();
    SettingsViewController settingsViewController;

    public MainView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void initialize(){
        firstNameText.setText(LoggedInUser.getInstance().getFirstName());
        navigateToDashboardView();
        settingsViewController = new SettingsViewController(templateRootAnchorPane);
    }

    @FXML
    public void navigateToWorkout() {
        setViewTo(workoutView);
    }

   @FXML
   public void navigateToNutritionView() {
        if (LoggedInUser.getInstance().getCalorieGoal() == 0) {
            setViewTo(calorieIntakeCalculatorController);
        } else {
            setViewTo(nutritionViewController);
        }
   }

   @FXML
   public void navigateToSettingsView() { setViewTo(settingsViewController);}

    @FXML
    public void navigateToDashboardView() {
        setViewTo(new DashboardViewController());
    }

    @FXML
    public void navigateToFriendsView() {
        setViewTo(new FriendsViewController());
    }

    private void setViewTo(AnchorPane pane){
        contentRootAnchorPane.getChildren().clear();
        contentRootAnchorPane.getChildren().add(pane);

        contentRootAnchorPane.setRightAnchor(pane, 0.0);
        contentRootAnchorPane.setTopAnchor(pane, 0.0);
        contentRootAnchorPane.setBottomAnchor(pane, 0.0);
    }

}
