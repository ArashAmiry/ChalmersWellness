package com.example.chalmerswellness;

import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CalorieIntakeCalculatorController extends AnchorPane implements Initializable, Observable {

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
    LinkedHashMap<String, Double> activityLevels  = new LinkedHashMap<>() {{
        put("Sedentary", 1.2);
        put("Lightly active", 1.375);
        put("Moderately active", 1.55);
        put("Active", 1.725);
        put("Very active", 1.9);
    }};
    @FXML
    private ComboBox<String> activityComboBox;
    @FXML
    private Text calorieIntakeText;
    @FXML
    private TextField ageTextField, weightTextField, weightGoalTextField, heightTextField;
    private ToggleGroup gender = new ToggleGroup();
    private ToggleGroup paceGroup = new ToggleGroup();
    private static List<Observer> observers = new ArrayList<>();


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

        for (Map.Entry<String, Double> entry : activityLevels.entrySet()) {
            String key = entry.getKey();
            activityComboBox.getItems().add(key);
        }
    }

    @FXML
    private void handleSubmitButton(MouseEvent event) {
        if (ageTextField.getText().length() == 0) {
            ageTextField.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        }
    }

    @FXML
    private void calculateCalorieIntake(MouseEvent mouseEvent) {
        double weightGoal = Double.parseDouble(weightGoalTextField.getText());
        profile.setWeightGoal(weightGoal);
        double weight = Double.parseDouble(weightTextField.getText());
        double height = Double.parseDouble(heightTextField.getText());
        int age = Integer.parseInt(ageTextField.getText());
        int pace = (int) paceGroup.getSelectedToggle().getUserData();
        boolean isMale = (boolean) gender.getSelectedToggle().getUserData();
        double activityLevel = activityLevels.get(activityComboBox.getValue());
        if (Integer.parseInt(weightGoalTextField.getText()) - weight < 0) {
            pace = -pace;
        }
        if (weight == weightGoal) {
            pace = 0;
        }

        int calorieIntake = CalorieCalculator.calculateCalorieIntake(isMale, weight, height, age, activityLevel, pace);
        calorieIntakeText.setText("Your recommended calorie intake is " + calorieIntake + " calories per day.");
        calorieIntakeText.setVisible(true);

        profile.setHasCalculatedCalorieIntake(true);
        profile.setCalorieGoal(calorieIntake);
        notifyObservers();
    }

    @Override
    public void notifyObservers() {
        for (var observer: observers) {
            observer.update(this);
        }
    }


    public void subscribe(Observer observer) {
        observers.add(observer);
    }
}
