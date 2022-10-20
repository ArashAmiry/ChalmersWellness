package com.example.chalmerswellness.Services.FriendServices;

import com.example.chalmerswellness.ObjectModels.User;

import java.util.List;

public interface IDatabaseFriendRepository {

    List<User> findFriends(String friendName);

    void insertFollow(int followingID);

    void removeFollow(int following_id);

    boolean alreadyFollowing(int followerID, int followingID);

}
