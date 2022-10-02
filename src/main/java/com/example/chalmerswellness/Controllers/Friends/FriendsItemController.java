package com.example.chalmerswellness.Controllers.Friends;

import com.example.chalmerswellness.FriendSearcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FriendsItemController extends AnchorPane {

    FriendSearcher friendSearcher = new FriendSearcher();

    int userId;
    String firstName;
    String lastName;

    @FXML
    Label name;

    public FriendsItemController(int userId, String firstName, String lastName){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FriendsItem.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        name.setText(firstName + " " + lastName);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @FXML
    private void followFriend(){
        friendSearcher.followFriend(userId);
    }
}
