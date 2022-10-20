package com.example.chalmerswellness.Controllers;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.LoggedInUser;
import com.example.chalmerswellness.MainView;
import com.example.chalmerswellness.ObjectModels.User;
import com.example.chalmerswellness.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;

public class SignUpController extends AnchorPane {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private TextField weightTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private AnchorPane navigationPane;
    private ToggleGroup genderToggleGroup = new ToggleGroup();
    private final UserService userService = UserService.getInstance();

    private AnchorPane rootpane;

    public SignUpController(AnchorPane pane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SignUpView.fxml"));

        this.rootpane = pane;

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void initialize() {
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        maleRadioButton.setUserData(Gender.Male);
        femaleRadioButton.setUserData(Gender.Female);
    }

    @FXML
    private void signUp(MouseEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        Gender gender = (Gender) genderToggleGroup.getSelectedToggle().getUserData();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int height = Integer.parseInt(heightTextField.getText());
        double weight = Double.parseDouble(weightTextField.getText());
        LocalDate birthDate = birthDatePicker.getValue();

        User newUser = new User(username, password, firstName, lastName, gender, email, birthDate, height, weight);

        if (userService.checkIfUsernameExists(username)) {
            System.out.println("Username already exists");
            rootpane.getChildren().remove(this);
        } else {
            userService.insertUser(newUser);
            LoggedInUser.createInstance(userService.getUser(username, password));
            rootpane.getChildren().clear();
            rootpane.getChildren().add(new MainView());
        }
    }

    @FXML
    private void openLoginPage(MouseEvent event) {
        rootpane.getChildren().remove(this);
    }

}
