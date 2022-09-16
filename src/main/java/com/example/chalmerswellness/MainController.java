package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainController extends AnchorPane {
    @FXML AnchorPane contentRootAnchorPane;
    @FXML Button workoutBtn;


    WorkoutView workoutView = new WorkoutView();
    NutritionViewController nutritionViewController = new NutritionViewController();
    DashboardViewController dashboardViewController = new DashboardViewController();

    SettingsViewController settingsViewController = new SettingsViewController();

    public MainController(){
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
        setViewTo(nutritionViewController);
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
