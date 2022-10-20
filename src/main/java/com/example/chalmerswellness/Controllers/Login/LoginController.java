package com.example.chalmerswellness.Controllers.Login;

import com.example.chalmerswellness.Controllers.MainView;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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


    public LoginController() {

    }

    @FXML
    private void login() {
        final String username = usernameTextField.getText();
        final String password = passwordTextField.getText();
        if(userService.checkIfCredentialsAreCorrect(username, password)) {
            LoggedInUser.createInstance(userService.getUser(username,password));
            rootPane.getChildren().clear();
            rootPane.getChildren().setAll(new MainView());
        }
    }

    @FXML
    private void createNewAccount() {
        rootPane.getChildren().add(new SignUpController(rootPane));
    }

}
