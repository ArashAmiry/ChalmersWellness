package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
    @FXML AnchorPane parentPane;
    @FXML AnchorPane navigationPane;
    DataService dataService = new DataService();


    public SignUpController(AnchorPane parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SignUpView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        parentPane = parent;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void signUp(MouseEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String email = emailTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        int height = Integer.parseInt(heightTextField.getText());
        double weight = Double.parseDouble(weightTextField.getText());
        LocalDate birthDate = birthDatePicker.getValue();

        User newUser = new User(username, firstName, lastName, email, height, birthDate, weight);

        if (dataService.checkIfUsernameExists(username)) {
            System.out.println("Username already exists");
        } else {
            dataService.insertUser(newUser, password);
            parentPane.getChildren().remove(this);
        }
    }

}