package com.example.chalmerswellness.Controllers.Friends;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FriendsItemController extends AnchorPane {
    public FriendsItemController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FriendsItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
