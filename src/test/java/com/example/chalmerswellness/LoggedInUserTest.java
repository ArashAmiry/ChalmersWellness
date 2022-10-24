package com.example.chalmerswellness;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.ObjectModels.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class LoggedInUserTest {
    private static User user;

    @BeforeEach
    void setupEach() {
        LoggedInUser.destroyInstance();
        user = new User("username", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1);
    }

    @AfterAll
    static void tearDown() {
        LoggedInUser.destroyInstance();
    }

    @Test
    void createInstanceMethodShouldCreateInstance() {
        Assertions.assertNull(LoggedInUser.getInstance());
        LoggedInUser.createInstance(user);
        Assertions.assertNotNull(LoggedInUser.getInstance());
    }

    @Test
    void destroyInstanceMethodShouldDestroyInstance() {
        LoggedInUser.createInstance(user);
        Assertions.assertNotNull(LoggedInUser.getInstance());
        LoggedInUser.destroyInstance();
        Assertions.assertNull(LoggedInUser.getInstance());
    }

    @Test
    void getInstanceMethodShouldReturnInstance() {
        LoggedInUser.createInstance(user);
        Assertions.assertNotNull(LoggedInUser.getInstance());
    }

    @Test
    void updateInstanceMethodShouldUpdateInstance() {
        LoggedInUser.createInstance(user);
        User oldUser = LoggedInUser.getInstance();
        User updatedUser = new User("newUsername", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1);
        LoggedInUser.updateInstance(updatedUser);
        User newUser = LoggedInUser.getInstance();
        Assertions.assertNotEquals(oldUser, newUser);
    }

}
