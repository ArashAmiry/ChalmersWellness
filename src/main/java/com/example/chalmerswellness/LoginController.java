package com.example.chalmerswellness;

import com.example.chalmerswellness.Controllers.Profile.ProfileController;
import com.example.chalmerswellness.Services.UserServices.IDatabaseUserRepository;
import com.example.chalmerswellness.Services.UserServices.DatabaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AnchorPane implements Initializable {
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    AnchorPane navigationPane;
    @FXML
    AnchorPane rootPane;
    private final IDatabaseUserRepository userService;


    public LoginController() {
        userService = new DatabaseUserRepository();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void login(MouseEvent event) {
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
    void createNewAccount(MouseEvent event) {
        rootPane.getChildren().add(new SignUpController(rootPane));
    }

}
