package com.example.chalmerswellness;

import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Services.DatabaseConnector;
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

    private static WorkoutModel model;
    private static UserService userService;

    @BeforeAll
    static void setup() {
        //TODO databaseConnector should probably be created in DbConnecitonService?
        DatabaseConnector d = new DatabaseConnector();


        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        UserService.getInstance().deleteAllUsers();
        userService = UserService.getInstance();
        userService.insertUser(new User("username", "password", "firstName", "lastName", Gender.Male, "email", LocalDate.now(),1, 1));
        User user = userService.getUser("username", "password");
        LoggedInUser.createInstance(user);
        WorkoutService.createInstance(new DatabaseWorkoutRepository());

        exerciseSetup();
    }

    private static void exerciseSetup(){
        Exercise exercise = new Exercise(0, "name", "type", "muscle", "equipment", "difficulty", "instructions");
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        WorkoutService.getInstance().insertExercises(exercises);
    }

    @BeforeEach
    void setupEach() {
        model = new WorkoutModel();
    }

    @Test
    void TestAddExerciseToActiveWorkout() {
        List<ExerciseItem> completedExercises = model.getTodayCompletedExercises();
        Assertions.assertEquals(true, completedExercises.size() == 0);


        int exercisesInitCount = completedExercises.size();
        model.addExerciseToActiveWorkout(model.getExercises().get(0));
        completedExercises = model.getTodayCompletedExercises();
        Assertions.assertTrue(exercisesInitCount < completedExercises.size());

        ExerciseItem exerciseItem = completedExercises.get(0);
        exerciseItem.getName();
    }

    @Test
    void TestSearchExercises() {
        Exercise exercise = model.getExercises().get(0);
        List<Exercise> searchResult = model.searchExercises(exercise.getName());
        String exerciseName = searchResult.get(0).getName();

        Assertions.assertEquals(exercise.getName(), exerciseName);
    }

    @Test
    void TestRemoveSet() {
        Exercise exercise = model.getExercises().get(0);
        model.addExerciseToActiveWorkout(exercise);
        ExerciseItem exerciseItem = model.getTodayCompletedExercises().get(0);

        float weight = 30;
        int reps = 5;

        ExerciseItemSet set = new ExerciseItemSet(weight, reps);
        exerciseItem.addSet(set);
        Assertions.assertEquals(weight, exerciseItem.getSets().get(0).getWeight());
        Assertions.assertEquals(reps, exerciseItem.getSets().get(0).getReps());

        model.updateCompletedExercise(exerciseItem);
        exerciseItem = model.getTodayCompletedExercises().get(0);
        List<ExerciseItemSet> exerciseItemSets = exerciseItem.getSets();
        Assertions.assertEquals(true, exerciseItemSets.size() >= 1);
    }

    @Test
    void TestGetExercises() {
        List<Exercise> exercises = model.getExercises();
        Assertions.assertEquals(true, exercises.size() >= 1);
    }

    @Test
    void TestGetTodayCompletedExercises() {
        List<ExerciseItem> exerciseItems = model.getTodayCompletedExercises();

        //TODO check if zero before proceeding


        Exercise exercise = model.getExercises().get(0);
        model.addExerciseToActiveWorkout(exercise);

        exerciseItems = model.getTodayCompletedExercises();
        Assertions.assertEquals(true, exerciseItems.size()>=1);
    }

    @Test
    void TestRemoveCompletedExercise() {
        //model.removeCompletedExercise();
    }

    @Test
    void TestUpdateCompletedExercise() {
        //model.updateCompletedExercise();
    }

    @Test
    void TestAddExercisesFromWorkout() {
        //model.addExercisesFromWorkout();
    }

    @Test
    void TestRemoveWorkout() {
        //model.removeWorkout();
    }

    @Test
    void TestGetSavedWorkouts() {
        //model.getSavedWorkouts();
    }

    @Test
    void TestAddWorkout() {
        //model.addWorkout();
    }
}

