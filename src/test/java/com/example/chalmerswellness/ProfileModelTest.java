package com.example.chalmerswellness;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.AccountModel.ProfileModel;
import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.User;
import com.example.chalmerswellness.Models.Services.DatabaseConnector;
import com.example.chalmerswellness.Models.Services.DbConnectionService;
import com.example.chalmerswellness.Models.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import com.example.chalmerswellness.Models.Services.WorkoutServices.DatabaseWorkoutRepository;
import com.example.chalmerswellness.Models.Services.WorkoutServices.WorkoutService;
import com.example.chalmerswellness.Models.WorkoutModel.WorkoutModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class ProfileModelTest {

    private static ProfileModel profileModel;
    private static UserService userService;
    private static User user;
    private static WorkoutModel workoutModel;

    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        WorkoutService.createInstance(new DatabaseWorkoutRepository());
    }

    @BeforeEach
    void setupEach() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        exerciseSetup();
        profileModel = new ProfileModel();
        userService = UserService.getInstance();
        workoutModel = new WorkoutModel();
        userService.insertUser(new User("user", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        user = userService.getUser("user", "password");
        LoggedInUser.createInstance(user);
    }

    private static void exerciseSetup(){
        Exercise exercise = new Exercise(0, "name", "type", "muscle", "equipment", "difficulty", "instructions");
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        WorkoutService.getInstance().insertExercises(exercises);
    }

    @Test
    void getCompletedExercisesMethodShouldReturnCompletedExercises() {
        int completedExercises = profileModel.getCompletedExercises(LocalDate.now(), user.getId()).size();
        Assertions.assertEquals(0, completedExercises);
        ExerciseItem exerciseItem = new ExerciseItem(workoutModel.getExercises().get(0));
        workoutModel.addExerciseToActiveWorkout(exerciseItem);
        int completedExercisesAfterAddingExercise = profileModel.getCompletedExercises(LocalDate.now(), user.getId()).size();
        Assertions.assertEquals(1, completedExercisesAfterAddingExercise);


    }

}
