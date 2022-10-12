package com.example.chalmerswellness.Controllers.Friends;

import com.example.chalmerswellness.Controllers.Profile.ProfileController;
import com.example.chalmerswellness.FriendSearcher;
import com.example.chalmerswellness.LoggedInUser;
import com.example.chalmerswellness.Services.FriendServices.DatabaseFriendRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FriendsItemController extends AnchorPane {

    FriendSearcher friendSearcher = new FriendSearcher();
    DatabaseFriendRepository friendService = new DatabaseFriendRepository();

    int userId;
    String firstName;
    String lastName;
    AnchorPane rootpane;

    @FXML
    Button followButton;

    @FXML
    Label name;

    public FriendsItemController(int userId, String firstName, String lastName, AnchorPane rootpane){
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
        this.rootpane = rootpane;
        checkFollow();
    }

    @FXML
    private void followFriend(){
        friendSearcher.followFriend(userId);
        checkFollow();
    }

    private void checkFollow(){
        if (friendService.alreadyFollowing(LoggedInUser.getInstance().getId(), this.userId)){
            followButton.setStyle("-fx-background-color: linear-gradient(to top left, #f1f1f1, #ffffff); -fx-text-fill: black;");
            followButton.setText("Unfollow");
        }
        else{
            followButton.setStyle("-fx-background-color: linear-gradient(to top left, #00d2ff, #0096FF); -fx-text-fill: white;");
            followButton.setText("Follow");
        }
    }

    @FXML
    private void visitProfilePage(){
        rootpane.getChildren().clear();
        rootpane.getChildren().add(new ProfileController(userId));
    }
}
