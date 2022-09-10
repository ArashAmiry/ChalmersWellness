package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainController extends AnchorPane {
    @FXML AnchorPane contentRootAnchorPane;
    @FXML Button workoutBtn;


    WorkoutViewController workoutViewController = new WorkoutViewController();
    NutritionViewController nutritionViewController = new NutritionViewController();
    DashboardViewController dashboardViewController = new DashboardViewController();

    public MainController(){
    }

    @FXML
    public void initialize(){
        setViewTo(dashboardViewController);
    }

    @FXML
    public void clickMe() {
        workoutBtn.textProperty().set("ssss");
        setViewTo(workoutViewController);
    }

   @FXML
   public void navigateToNutritionView() {
        setViewTo(nutritionViewController);
   }

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
