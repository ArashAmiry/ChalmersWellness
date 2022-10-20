package com.example.chalmerswellness.Controllers.Nutrition;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.FoodModel.CalorieCalculator;
import com.example.chalmerswellness.Models.ObjectModels.User;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CalorieIntakeCalculatorController extends AnchorPane implements Initializable{
    @FXML
    private RadioButton radioButtonSlowPace;
    @FXML
    private RadioButton radioButtonMediumPace;
    @FXML
    private RadioButton radioButtonFastPace;
    LinkedHashMap<String, Double> activityLevels  = new LinkedHashMap<>(); {
        {
            activityLevels.put("Sedentary", 1.2);
            activityLevels.put("Lightly active", 1.375);
            activityLevels.put("Moderately active", 1.55);
            activityLevels.put("Active", 1.725);
            activityLevels.put("Very active", 1.9);
        }
    }
    @FXML
    private ComboBox<String> activityComboBox;
    @FXML
    private TextField weightGoalTextField;
    @FXML
    private AnchorPane rootPane;
    private ToggleGroup paceGroup = new ToggleGroup();
    private final UserService userService = UserService.getInstance();

    public CalorieIntakeCalculatorController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CalorieIntakeCalculator.fxml"));

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
        radioButtonSlowPace.setToggleGroup(paceGroup);
        radioButtonMediumPace.setToggleGroup(paceGroup);
        radioButtonFastPace.setToggleGroup(paceGroup);
        radioButtonSlowPace.setUserData(250);
        radioButtonMediumPace.setUserData(500);
        radioButtonFastPace.setUserData(1000);

        for (Map.Entry<String, Double> entry : activityLevels.entrySet()) {
            String key = entry.getKey();
            activityComboBox.getItems().add(key);
        }
    }

    @FXML
    private void calculateCalorieIntake() {
        User loggedInUser = LoggedInUser.getInstance();
        double weight = loggedInUser.getWeight();
        double height = loggedInUser.getHeight();
        int age = loggedInUser.getAge();
        Gender gender = loggedInUser.getGender();
        double weightGoal = Double.parseDouble(weightGoalTextField.getText());
        int pace = (int) paceGroup.getSelectedToggle().getUserData();
        double activityLevel = activityLevels.get(activityComboBox.getValue());
        if (Double.parseDouble(weightGoalTextField.getText()) - weight < 0) {
            pace = -pace;
        }
        if (weight == weightGoal) {
            pace = 0;
        }

        int calorieIntake = CalorieCalculator.calculateCalorieIntake(gender, weight, height, age, activityLevel, pace);

        userService.setCalorieGoal(loggedInUser.getId(), calorieIntake);
        userService.setWeightGoal(loggedInUser.getId(), weightGoal);
        LoggedInUser.updateInstance(userService.getUser(loggedInUser.getId()));

        rootPane.getChildren().setAll(new NutritionViewController());
    }

}
