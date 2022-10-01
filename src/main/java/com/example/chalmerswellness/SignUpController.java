package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SignUpController extends AnchorPane implements Initializable {
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    TextField firstNameTextField;
    @FXML
    TextField lastNameTextField;
    @FXML
    TextField emailTextField;
    @FXML
    TextField heightTextField;
    @FXML
    TextField weightTextField;
    @FXML
    DatePicker birthDatePicker;
    @FXML
    RadioButton maleRadioButton;
    @FXML
    RadioButton femaleRadioButton;
    @FXML
    AnchorPane rootPane;

    ToggleGroup genderToggleGroup = new ToggleGroup();
    DataService dataService = new DataService();


    public SignUpController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        maleRadioButton.setUserData(Gender.Male);
        femaleRadioButton.setUserData(Gender.Female);
    }

    @FXML
    void signUp(MouseEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Gender gender = (Gender) genderToggleGroup.getSelectedToggle().getUserData();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int height = Integer.parseInt(heightTextField.getText());
        double weight = Double.parseDouble(weightTextField.getText());
        LocalDate birthDate = birthDatePicker.getValue();

        User newUser = new User(username, password, firstName, lastName, gender, email, height, birthDate, weight);

        if (!dataService.checkIfUsernameExists(username)) {
            dataService.insertUser(newUser);
            LoggedInUser.createInstance(dataService.getUser(username, password));
            FXMLLoader fxmlLoader = new FXMLLoader(ChalmersWellnessApp.class.getResource("/fxml/MainView.fxml"));
            rootPane.getChildren().setAll((Node) fxmlLoader.load());
        }
    }

}
