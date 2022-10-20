package com.example.chalmerswellness.Controllers.Friends;

import com.example.chalmerswellness.Models.FriendSearcher;
import com.example.chalmerswellness.ObjectModels.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;


public class FriendsViewController extends AnchorPane{

    @FXML
    AnchorPane rootpane;

    @FXML
    VBox friendList;

    @FXML
    TextField search;

    FriendSearcher friendSearcher = new FriendSearcher();

    public FriendsViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FriendsView.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        searchFriend();
    }

    @FXML
    private void searchFriend(){
        List<User> users = friendSearcher.findFriends(search.getText());
        friendList.getChildren().clear();

        for (User user : users){
            friendList.getChildren().add(new FriendsItemController(user.getId(), user.getFirstName(), user.getLastName(), rootpane));
        }
    }
}
