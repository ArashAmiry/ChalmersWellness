package com.example.chalmerswellness.Models.AccountModel;

import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.Services.FriendServices.FriendService;
import com.example.chalmerswellness.Models.ObjectModels.User;

import java.util.List;

public class FriendSearcher {
    private FriendService friendService = FriendService.getInstance();

    public List<User> findFriends(String friendName){
        return friendService.findFriends(friendName);
    }

    public void followFriend(int friendID){
        int followerID = LoggedInUser.getInstance().getId();
        if (friendService.alreadyFollowing(followerID, friendID)){
            friendService.removeFollow(friendID);
        }
        else{
            friendService.insertFollow(friendID);
        }

    }
}
