package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    @FXML AnchorPane navigationPane;
    ToggleGroup genderToggleGroup = new ToggleGroup();
    private final UserService userService = UserService.getInstance();

    AnchorPane rootpane;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        maleRadioButton.setUserData(Gender.Male);
        femaleRadioButton.setUserData(Gender.Female);
    }

    @FXML
    void signUp(MouseEvent event) {
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

}
