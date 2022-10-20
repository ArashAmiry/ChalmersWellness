package com.example.chalmerswellness.Services.FriendServices;

import com.example.chalmerswellness.ObjectModels.User;

import java.util.List;

public class FriendService {
    private static FriendService single_instance = null;
    private IDatabaseFriendRepository repository;
    private FriendService(IDatabaseFriendRepository workoutRepository)
    {
        repository = workoutRepository;
    }

    public static void createInstance(IDatabaseFriendRepository friendRepository){
        if(single_instance == null){
            single_instance = new FriendService(friendRepository);
        }
    }

    public static FriendService getInstance()
    {
        return single_instance;
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
