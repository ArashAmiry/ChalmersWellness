package com.example.chalmerswellness.Controllers.Settings;

import com.example.chalmerswellness.Gender;
import com.example.chalmerswellness.LoggedInUser;
import com.example.chalmerswellness.Services.UserServices.UserService;
import com.example.chalmerswellness.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SettingsViewController extends AnchorPane implements Initializable {
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
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    DatePicker birthDatePicker;
    @FXML
    RadioButton maleRadioButton;
    @FXML
    RadioButton femaleRadioButton;
    @FXML
    AnchorPane rootPane;
    ToggleGroup genderToggleGroup = new ToggleGroup();
    private final UserService userService = UserService.getInstance();

    public SettingsViewController(AnchorPane templateRootAnchorPane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SettingsView.fxml"));

        this.rootPane = templateRootAnchorPane;

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
        User user = LoggedInUser.getInstance();

        usernameTextField.setText(user.getUsername());
        passwordTextField.setText(user.getPassword());
        firstNameTextField.setText(user.getFirstName());
        lastNameTextField.setText(user.getLastName());
        emailTextField.setText(user.getEmail());
        heightTextField.setText(String.valueOf(user.getHeight()));
        weightTextField.setText(String.valueOf(user.getWeight()));
        birthDatePicker.setValue(user.getBirthDate());
        maleRadioButton.setToggleGroup(genderToggleGroup);
        femaleRadioButton.setToggleGroup(genderToggleGroup);
        maleRadioButton.setUserData(Gender.Male);
        femaleRadioButton.setUserData(Gender.Female);

        if(user.getGender().equals(Gender.Male)) {
            genderToggleGroup.selectToggle(maleRadioButton);
        } else {
            genderToggleGroup.selectToggle(femaleRadioButton);
        }
    }
    @FXML
    void saveChanges() {
        User user = LoggedInUser.getInstance();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        double weight = Double.parseDouble(weightTextField.getText());
        int height = Integer.parseInt(heightTextField.getText());
        LocalDate birthDate = birthDatePicker.getValue();
        Gender gender = (Gender) genderToggleGroup.getSelectedToggle().getUserData();

        userService.updateUser(user.getId(), username, password, firstName, lastName, gender, email, birthDate, height, weight);
        LoggedInUser.updateInstance(userService.getUser(user.getId()));
    }

    @FXML
    void logOut() throws IOException {
        LoggedInUser.destroyInstance();
        rootPane.getChildren().setAll((Node) new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml")).load());
    }

}
