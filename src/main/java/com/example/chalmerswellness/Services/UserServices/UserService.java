package com.example.chalmerswellness.Services.UserServices;

import com.example.chalmerswellness.Gender;
import com.example.chalmerswellness.User;

import java.time.LocalDate;

public class UserService {
    private IDatabaseUserRepository repository;
    private UserService(IDatabaseUserRepository repositoryType)
    {
        repository = repositoryType;
    }

    private static UserService single_instance = null;

    public static void createInstance(IDatabaseUserRepository repositoryType){
        if(single_instance == null){
            single_instance = new UserService(repositoryType);
        }
    }

    public static UserService getInstance()
    {
        return single_instance;
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void updateUser(int id, String username, String password, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, int height, double weight) {
        repository.updateUser(id, username, password, firstName, lastName, gender, email, birthDate, height, weight);
    }

    public User getUser(String username, String password) {
        return repository.getUser(username, password);
    }

    public User getUser(int id) {
        return repository.getUser(id);
    }

    public boolean checkIfCredentialsAreCorrect(String username, String password) {
        return repository.checkIfCredentialsAreCorrect(username, password);
    }

    public boolean checkIfUsernameExists(String username) {
        return repository.checkIfUsernameExists(username);
    }

    public void setCalorieGoal(int id, double calorieGoal) {
        repository.setCalorieGoal(id, calorieGoal);
    }

    public void setWeightGoal(int id, double weightGoal)   {
        repository.setWeightGoal(id, weightGoal);
    }

    public void deleteAllUsers(){
        repository.deleteAllUsers();
    }
}
