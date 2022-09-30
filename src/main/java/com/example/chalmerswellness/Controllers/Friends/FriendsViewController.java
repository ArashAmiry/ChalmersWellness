package com.example.chalmerswellness.Controllers.Friends;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FriendsViewController extends AnchorPane {

    @FXML
    VBox test;

    /*@FXML
    TextField search;*/

    public FriendsViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FriendsView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


 /*   @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }*/

    /*@FXML
    public void initialize(){
        friendList.getChildren().add(new FriendsItemController());
    }*/
}
