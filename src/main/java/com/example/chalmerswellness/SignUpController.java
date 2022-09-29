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

public class SignUpController extends AnchorPane implements Initializable {
    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordTextField;
    @FXML AnchorPane parentPane;
    DataService dataService = new DataService();


    public SignUpController(AnchorPane pane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SignUpView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        parentPane = pane;

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
        dataService.insertUser(username, password);
        parentPane.getChildren().remove(this);

    }

}
