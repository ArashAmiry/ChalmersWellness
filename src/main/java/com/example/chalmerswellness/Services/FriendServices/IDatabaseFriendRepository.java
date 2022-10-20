package com.example.chalmerswellness.Services.FriendServices;

import com.example.chalmerswellness.ObjectModels.User;

import java.util.List;

public interface IDatabaseFriendRepository {

    List<User> findFriends(String friendName);

    void insertFollow(int followingId);

    void removeFollow(int followingId);

    boolean alreadyFollowing(int followerId, int followingId);

}
