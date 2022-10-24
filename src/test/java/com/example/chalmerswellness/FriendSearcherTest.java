package com.example.chalmerswellness;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.AccountModel.FriendSearcher;
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
import java.util.List;

class FriendSearcherTest {
    private static FriendSearcher friendSearcher;
    private static FriendService friendService;
    private static UserService userService;

    private static User user1;

    private static User user2;

    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        FriendService.createInstance(new DatabaseFriendRepository());
    }

    @BeforeEach
    void setupEach() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        friendService = FriendService.getInstance();
        userService = UserService.getInstance();
        friendSearcher = new FriendSearcher();
        userService.insertUser(new User("user1", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        userService.insertUser(new User("user2", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        user1 = userService.getUser("user1", "password");
        user2 = userService.getUser("user2", "password");
        LoggedInUser.createInstance(user1);


    }

    @Test
    void findFriendsMethodShouldReturnListOfUsers() {
        List<User> users = friendSearcher.findFriends("user");
        Assertions.assertNotNull(users);
    }

    @Test
    void followFriendMethodShouldFollowFriend() {
        friendSearcher.followFriend(1);
        Assertions.assertTrue(friendService.alreadyFollowing(1, 1));
    }
}
