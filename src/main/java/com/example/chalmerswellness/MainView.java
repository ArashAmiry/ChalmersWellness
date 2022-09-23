package com.example.chalmerswellness;

import com.example.chalmerswellness.Controllers.WorkoutController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainView extends AnchorPane {
    @FXML AnchorPane contentRootAnchorPane;
    @FXML Button workoutBtn;

    WorkoutController workoutView = new WorkoutController();
    NutritionViewController nutritionViewController = new NutritionViewController();

    CalorieIntakeCalculatorController calorieIntakeCalculatorController = new CalorieIntakeCalculatorController();
    DashboardViewController dashboardViewController = new DashboardViewController();

    SettingsViewController settingsViewController = new SettingsViewController();

    public MainView(){
    }

    @FXML
    public void initialize(){
        setViewTo(dashboardViewController);
    }

    @FXML
    public void navigateToWorkout() {
        setViewTo(workoutView);
    }

   @FXML
   public void navigateToNutritionView() {
        if (!Profile.getInstance().hasCalculatedCalorieIntake()) {
            setViewTo(calorieIntakeCalculatorController);
        } else {
            setViewTo(nutritionViewController);
        }
   }
   @FXML
   public void navigateToSettingsView() { setViewTo(settingsViewController);}

    @FXML
    public void navigateToDashboardView() {
        setViewTo(dashboardViewController);
    }

    private void setViewTo(AnchorPane pane){
        contentRootAnchorPane.getChildren().clear();
        contentRootAnchorPane.getChildren().add(pane);

        contentRootAnchorPane.setRightAnchor(pane, 0.0);
        contentRootAnchorPane.setTopAnchor(pane, 0.0);
        contentRootAnchorPane.setBottomAnchor(pane, 0.0);
    }
}
