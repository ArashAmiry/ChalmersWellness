package com.example.chalmerswellness;

import com.example.chalmerswellness.Controllers.Dashboard.DashboardViewController;
import com.example.chalmerswellness.Controllers.Nutrition.NutritionViewController;
import com.example.chalmerswellness.Controllers.Settings.SettingsViewController;
import com.example.chalmerswellness.Controllers.Workout.WorkoutController;
import com.example.chalmerswellness.Services.DataService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MainView extends AnchorPane {
    @FXML AnchorPane contentRootAnchorPane;
    @FXML AnchorPane navigationAnchorPane;
    @FXML AnchorPane templateRootAnchorPane;
    @FXML Button workoutBtn;
    @FXML Text firstNameText;
    DataService dataService = new DataService();

    public MainView(){
    }

    @FXML
    public void initialize(){
        if (dataService.checkIfUsersExist()) {
            openLoginScreen();
        } else {
            openSignUpScreen();
        }
    }

    @FXML
    public void navigateToWorkout() {
        setViewTo(new WorkoutController());
    }

   @FXML
   public void navigateToNutritionView() {
        if (LoggedInUser.getInstance().getCalorieGoal() == 0) {
            setViewTo(new CalorieIntakeCalculatorController());
        } else {
            setViewTo(new NutritionViewController());
        }
   }
   @FXML
   public void navigateToSettingsView() {
        setViewTo(new SettingsViewController());
    }

    @FXML
    public void navigateToDashboardView() {
        setViewTo(new DashboardViewController());
    }

    private void setViewTo(AnchorPane pane){
        contentRootAnchorPane.getChildren().clear();
        contentRootAnchorPane.getChildren().add(pane);

        contentRootAnchorPane.setRightAnchor(pane, 0.0);
        contentRootAnchorPane.setTopAnchor(pane, 0.0);
        contentRootAnchorPane.setBottomAnchor(pane, 0.0);
    }

    private void openLoginScreen() {
        templateRootAnchorPane.getChildren().add(new LoginController(templateRootAnchorPane, contentRootAnchorPane));
    }

    private void openSignUpScreen() {
        templateRootAnchorPane.getChildren().add(new SignUpController(templateRootAnchorPane, contentRootAnchorPane));
    }
}
