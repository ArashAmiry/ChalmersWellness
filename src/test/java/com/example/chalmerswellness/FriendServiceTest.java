package com.example.chalmerswellness;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.ObjectModels.User;
import com.example.chalmerswellness.Models.Services.DatabaseConnector;
import com.example.chalmerswellness.Models.Services.DbConnectionService;
import com.example.chalmerswellness.Models.Services.FriendServices.DatabaseFriendRepository;
import com.example.chalmerswellness.Models.Services.FriendServices.FriendService;
import com.example.chalmerswellness.Models.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class FriendServiceTest {

    private static FriendService friendService;
    private static UserService userService;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void setup(){
        DbConnectionService.createInstance(false);
        FriendService.createInstance(new DatabaseFriendRepository());
        friendService = FriendService.getInstance();
        UserService.createInstance(new DatabaseUserRepository());
        userService = UserService.getInstance();
    }

    @BeforeEach
    void setupEach(){
        DatabaseConnector dbConnector = new DatabaseConnector();
        userService.insertUser(new User("user1", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        userService.insertUser(new User("user2", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        user1 = userService.getUser("user1", "password");
        user2 = userService.getUser("user2", "password");
        LoggedInUser.createInstance(user1);
    }

    @Test
    void insertFollowMethodShouldInsertFollow(){
        friendService.insertFollow(user2.getId());
        Assertions.assertTrue(friendService.alreadyFollowing(user1.getId(), user2.getId()));
    }

    @Test
    void removeFollowMethodShouldRemoveFollow(){
        friendService.insertFollow(user2.getId());
        friendService.removeFollow(user2.getId());
        Assertions.assertFalse(friendService.alreadyFollowing(user1.getId(), user2.getId()));
    }

    @Test
    void findFriendsMethodShouldReturnFriends(){
        friendService.insertFollow(user2.getId());
        Assertions.assertTrue(friendService.findFriends("user2").size() > 0);
    }

    @Test
    void alreadyFollowingMethodShouldReturnTrueIfAlreadyFollowing(){
        friendService.insertFollow(user2.getId());
        Assertions.assertTrue(friendService.alreadyFollowing(user1.getId(), user2.getId()));
    }





}
