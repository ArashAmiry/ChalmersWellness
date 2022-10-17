package com.example.chalmerswellness.Services.UserServices;

import com.example.chalmerswellness.Gender;
import com.example.chalmerswellness.User;

import java.time.LocalDate;

public interface IDatabaseUserRepository {

    void insertUser(User user);

    void updateUser(int id, String username, String password, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, int height, double weight);

    User getUser(String username, String password);

    User getUser(int id);

    boolean checkIfCredentialsAreCorrect(String username, String password);

    boolean checkIfUsernameExists(String username);

    void setCalorieGoal(int id, double calorieGoal);

    void setWeightGoal(int id, double weightGoal);

}
