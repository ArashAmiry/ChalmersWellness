package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AnchorPane implements Initializable {
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    AnchorPane parentPane;
    @FXML
    AnchorPane navigationPane;
    DataService dataService = new DataService();


    public LoginController(AnchorPane parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));

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
    void login(MouseEvent event) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(dataService.loginUser(username, password)) {
            System.out.println("Login successful");
            LoggedInUser.createInstance(dataService.getUser(username,password));
            parentPane.getChildren().remove(this);
        } else {
            System.out.println("Login failed");
        }
    }

    @FXML
    void createNewAccount(MouseEvent event) {
        SignUpController signUpController = new SignUpController(parentPane);
        parentPane.getChildren().remove(this);
        parentPane.getChildren().add(signUpController);
    }

}
