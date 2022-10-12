package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DbConnectionService;
import com.example.chalmerswellness.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Services.UserServices.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserServiceTest {

    private static UserService userService;
    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        UserService.getInstance().deleteAllUsers();
    }

    @BeforeEach
    void setupEach() {
        UserService.getInstance().deleteAllUsers();
        UserService.createInstance(new DatabaseUserRepository());
        userService = UserService.getInstance();
        userService.insertUser(new User("username", "password", "firstName", "lastName", Gender.Male, "email", LocalDate.now(),1, 1));
    }

    @Test
    void updateUserMethodShouldUpdateUser() {
        User user = userService.getUser("username", "password");
        userService.updateUser(user.getId(), "username2", "password", "firstName", "lastName", Gender.Male, "email",  LocalDate.now(), 1, 1);
        Assertions.assertTrue(userService.checkIfUsernameExists("username2"));
    }

    @Test
    void getUserMethodShouldReturnUser() {
        User user = userService.getUser("username", "password");
        Assertions.assertTrue(userService.getUser("username", "password") != null);
        Assertions.assertTrue(userService.getUser(user.getId()) != null);
    }

    @Test
    void checkIfCredentialsAreCorrectMethodShouldReturnTrueIfCredentialsAreCorrect() {
        Assertions.assertTrue(userService.checkIfCredentialsAreCorrect("username", "password"));
    }

    @Test
    void checkIfUsernameExistsMethodShouldReturnTrueIfUsernameExists() {
        Assertions.assertTrue(userService.checkIfUsernameExists("username"));
    }

    @Test
    void setCalorieGoalMethodShouldSetCalorieGoal() {
        userService.setCalorieGoal(userService.getUser("username", "password").getId(), 1000);
        Assertions.assertEquals(1000, userService.getUser("username", "password").getCalorieGoal());
    }

    @Test
    void setWeightGoalMethodShouldSetWeightGoal() {
        userService.setWeightGoal(userService.getUser("username", "password").getId(), 1000);
        Assertions.assertEquals(1000, userService.getUser("username", "password").getWeightGoal());
    }

    @Test
    void deleteAllUsersMethodShouldDeleteAllUsers() {
        userService.deleteAllUsers();
        Assertions.assertTrue(userService.getUser("username", "password") == null);
    }
}

