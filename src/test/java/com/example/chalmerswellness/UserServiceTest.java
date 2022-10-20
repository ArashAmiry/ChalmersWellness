package com.example.chalmerswellness;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.ObjectModels.User;
import com.example.chalmerswellness.Models.Services.DatabaseConnector;
import com.example.chalmerswellness.Models.Services.DbConnectionService;
import com.example.chalmerswellness.Models.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UserServiceTest {

    private static UserService userService;
    private User user;
    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
    }

    @BeforeEach
    void setupEach() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        userService = UserService.getInstance();
        userService.insertUser(new User("username", "password", "firstName", "lastName", Gender.Male, "email", LocalDate.now(),1, 1));
        LoggedInUser.createInstance(userService.getUser("username", "password"));
        user = LoggedInUser.getInstance();
    }

    @Test
    void updateUserMethodShouldUpdateUser() {
        userService.updateUser(user.getId(), "username2", "password", "firstName", "lastName", Gender.Male, "email",  LocalDate.now(), 1, 1);
        Assertions.assertTrue(userService.checkIfUsernameExists("username2"));
    }

    @Test
    void getUserMethodShouldReturnUser() {
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
        userService.setCalorieGoal(user.getId(), 1000);
        Assertions.assertEquals(1000, userService.getUser(user.getId()).getCalorieGoal());
    }

    @Test
    void setWeightGoalMethodShouldSetWeightGoal() {
        userService.setWeightGoal(user.getId(), 1000);
        Assertions.assertEquals(1000, userService.getUser(user.getId()).getWeightGoal());
    }

}

