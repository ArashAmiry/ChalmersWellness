package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.FriendService;

import java.util.List;

public class FriendSearcher {
    FriendService friendService = new FriendService();

    public List<User> findFriends(String friendName){
        return friendService.findFriends(friendName);
    }

    public void followFriend(int friendID){
        int followerID = LoggedInUser.getInstance().getId();

        friendService.insertFollow(followerID, friendID);
    }
}
