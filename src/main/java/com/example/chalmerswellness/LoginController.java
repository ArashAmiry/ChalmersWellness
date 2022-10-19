package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginController extends AnchorPane {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private AnchorPane navigationPane;
    @FXML
    private AnchorPane rootPane;
    private final UserService userService = UserService.getInstance();

    @FXML
    private void login() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(userService.checkIfCredentialsAreCorrect(username, password)) {
            System.out.println("Login successful");
            LoggedInUser.createInstance(userService.getUser(username,password));
            rootPane.getChildren().clear();
            rootPane.getChildren().setAll(new MainView());
        } else {
            System.out.println("Login failed");
        }
    }

    @FXML
    private void createNewAccount() {
        rootPane.getChildren().add(new SignUpController(rootPane));
    }

}
