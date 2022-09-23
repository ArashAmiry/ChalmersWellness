package com.example.chalmerswellness;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CalorieIntakeCalculatorController extends AnchorPane implements Initializable {

    final private Profile profile = Profile.getInstance();
    @FXML
    private RadioButton radioButtonMale;
    @FXML
    private RadioButton radioButtonFemale;
    @FXML
    private RadioButton radioButtonSlowPace;
    @FXML
    private RadioButton radioButtonMediumPace;
    @FXML
    private RadioButton radioButtonFastPace;
    @FXML
    private ChoiceBox<Double> activityChoiceBox;
    private Double[] activityLevels = {1.2, 1.375, 1.55, 1.725, 1.9};
    @FXML
    private Text calorieIntakeText;
    @FXML
    private TextField ageTextField, weightTextField, weightGoalTextField, heightTextField;
    private ToggleGroup gender = new ToggleGroup();
    private ToggleGroup paceGroup = new ToggleGroup();
    private int pace;
    private double weight;
    private double height;
    private int age;
    private double activityLevel;
    private boolean isMale;
    private int recommendedCalorieIntake;


    public CalorieIntakeCalculatorController(){
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
        radioButtonMale.setToggleGroup(gender);
        radioButtonFemale.setToggleGroup(gender);
        radioButtonMale.setUserData(true);
        radioButtonFemale.setUserData(false);
        radioButtonSlowPace.setToggleGroup(paceGroup);
        radioButtonMediumPace.setToggleGroup(paceGroup);
        radioButtonFastPace.setToggleGroup(paceGroup);
        radioButtonSlowPace.setUserData(250);
        radioButtonMediumPace.setUserData(500);
        radioButtonFastPace.setUserData(1000);
        activityChoiceBox.getItems().addAll(activityLevels);
    }

    @FXML
    private void calculateCalorieIntake(MouseEvent mouseEvent) {
        profile.setWeightGoal(Integer.parseInt(weightGoalTextField.getText()));
        weight = Double.parseDouble(weightTextField.getText());
        height = Double.parseDouble(heightTextField.getText());
        age = Integer.parseInt(ageTextField.getText());
        pace = (int) paceGroup.getSelectedToggle().getUserData();
        if (Integer.parseInt(weightGoalTextField.getText()) - weight < 0) {
            pace = -pace;
        }
        isMale = (boolean) gender.getSelectedToggle().getUserData();
        activityLevel = activityChoiceBox.getValue();
        recommendedCalorieIntake = CalorieCalculator.calculateCalorieIntake(isMale, weight, height, age, activityLevel, pace);
        calorieIntakeText.setText(String.valueOf(recommendedCalorieIntake));
        profile.setHasCalculatedCalorieIntake(true);
        profile.setCalorieGoal(recommendedCalorieIntake);
    }


}
