package com.example.chalmerswellness;

import com.example.chalmerswellness.Controllers.Dashboard.DashboardViewController;
import com.example.chalmerswellness.Controllers.Friends.FriendsItemController;
import com.example.chalmerswellness.Controllers.Friends.FriendsViewController;
import com.example.chalmerswellness.Controllers.Nutrition.NutritionViewController;
import com.example.chalmerswellness.Controllers.Settings.SettingsViewController;
import com.example.chalmerswellness.Controllers.Workout.WorkoutController;
import com.example.chalmerswellness.Services.DataService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainView extends AnchorPane {
    @FXML AnchorPane contentRootAnchorPane;
    @FXML AnchorPane navigationAnchorPane;
    @FXML AnchorPane templateRootAnchorPane;
    @FXML Button workoutBtn;

    WorkoutController workoutView = new WorkoutController();
    NutritionViewController nutritionViewController = new NutritionViewController();
    FriendsViewController friendsViewController = new FriendsViewController();
    CalorieIntakeCalculatorController calorieIntakeCalculatorController = new CalorieIntakeCalculatorController();
    DashboardViewController dashboardViewController = new DashboardViewController();
    SettingsViewController settingsViewController = new SettingsViewController();
    DataService dataService = new DataService();

    public MainView(){
    }

    @FXML
    public void initialize(){
        navigateToDashboardView();
        if (dataService.checkIfUsersExist()) {
            openLoginScreen();
        } else {
            openSignUpScreen();
        }
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

    @FXML
    public void navigateToFriendsView() {
        setViewTo(friendsViewController);
    }

    private void setViewTo(AnchorPane pane){
        contentRootAnchorPane.getChildren().clear();
        contentRootAnchorPane.getChildren().add(pane);

        contentRootAnchorPane.setRightAnchor(pane, 0.0);
        contentRootAnchorPane.setTopAnchor(pane, 0.0);
        contentRootAnchorPane.setBottomAnchor(pane, 0.0);
    }

    private void openLoginScreen() {
        LoginController loginController = new LoginController(templateRootAnchorPane);
        templateRootAnchorPane.getChildren().add(loginController);
    }

    private void openSignUpScreen() {
        SignUpController signUpController = new SignUpController(templateRootAnchorPane);
        templateRootAnchorPane.getChildren().add(signUpController);
    }
}
