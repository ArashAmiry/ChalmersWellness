package com.example.chalmerswellness.Models.Services.FriendServices;

import com.example.chalmerswellness.Models.ObjectModels.User;

import java.util.List;

public final class FriendService {
    private static FriendService friendService = null;
    private IDatabaseFriendRepository repository;
    private FriendService(IDatabaseFriendRepository workoutRepository)
    {
        repository = workoutRepository;
    }

    public static void createInstance(IDatabaseFriendRepository friendRepository){
        if (friendService == null) {
            friendService = new FriendService(friendRepository);
        }
    }

    public static FriendService getInstance()
    {
        return friendService;
    }

    public List<User> findFriends(String friendName) {
        return repository.findFriends(friendName);
    }

    public void insertFollow(int followingID) {
        repository.insertFollow(followingID);
    }

    public void removeFollow(int following_id) {
        repository.removeFollow(following_id);
    }

    public boolean alreadyFollowing(int followerID, int followingID) {
        return repository.alreadyFollowing(followerID, followingID);
    }


}
