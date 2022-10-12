package com.example.chalmerswellness;

import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Services.DbConnectionService;
import com.example.chalmerswellness.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Services.UserServices.UserService;
import com.example.chalmerswellness.Services.WorkoutServices.DatabaseWorkoutRepository;
import com.example.chalmerswellness.Services.WorkoutServices.WorkoutService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class WorkoutTest {

    private static WorkoutService workoutService;
    private static WorkoutModel model;

    private static UserService userService;

    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        UserService.getInstance().deleteAllUsers();
        userService = UserService.getInstance();
        userService.insertUser(new User("username", "password", "firstName", "lastName", Gender.Male, "email", LocalDate.now(),1, 1));
        User user = userService.getUser("username", "password");
        LoggedInUser.createInstance(user);

        WorkoutService.createInstance(new DatabaseWorkoutRepository());
        workoutService = WorkoutService.getInstance();
    }

    @BeforeEach
    void setupEach() {
        model = new WorkoutModel();
    }

    @Test
    void TestAddExerciseToActiveWorkout() {
        var completedExercises = model.getTodayCompletedExercises();
        int exercisesInitCount = completedExercises.size();
        model.addExerciseToActiveWorkout(model.getExercises().get(0));
        completedExercises = model.getTodayCompletedExercises();
        Assertions.assertTrue(exercisesInitCount < completedExercises.size());

        ExerciseItem exerciseItem = completedExercises.get(0);
        exerciseItem.getName();
    }

    @Test
    void TestSearchExercises() {
        var exercise = model.getExercises().get(0);
        List<Exercise> searchResult = model.searchExercises(exercise.getName());
        String exerciseName = searchResult.get(0).getName();

        Assertions.assertEquals(exercise.getName(), exerciseName);
    }

    @Test
    void TestRemoveSet() {



        //model.removeSet();
    }

    //public void removeSet(ExerciseItem exerciseItem, ExerciseItemSet set);
    //public void updateCompletedExercise(ExerciseItem exerciseItem);
    //public List<Exercise> getExercises();
    //public List<ExerciseItem> getTodayCompletedExercises();
    //private List<ExerciseItem> sortCompletedExercises(List<ExerciseItem> exerciseItems);
    //public void removeCompletedExercise(ExerciseItem exerciseItem);
    //public void addExercisesFromWorkout(Workout workout);

    //public void removeWorkout(Workout workout);
    /*public List<Workout> getSavedWorkouts()
    //public void addWorkout(Workout workout);*/

}

