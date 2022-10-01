package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DataService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController extends AnchorPane {
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane parentPane;
    @FXML
    AnchorPane contentPane;
    @FXML
    AnchorPane navigationPane;
    DataService dataService = new DataService();


    public LoginController() {
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(dataService.checkIfCredentialsMatch(username, password)) {
            LoggedInUser.createInstance(dataService.getUser(username,password));
            FXMLLoader fxmlLoader = new FXMLLoader(ChalmersWellnessApp.class.getResource("/fxml/MainView.fxml"));
            rootPane.getChildren().setAll((Node) fxmlLoader.load());
        }
    }

    @FXML
    void createNewAccount(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChalmersWellnessApp.class.getResource("/fxml/SignUpView.fxml"));
        rootPane.getChildren().setAll((Node) fxmlLoader.load());
    }

}
