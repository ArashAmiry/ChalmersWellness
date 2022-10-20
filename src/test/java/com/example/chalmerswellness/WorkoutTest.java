package com.example.chalmerswellness;

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
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        userService = UserService.getInstance();
        userService.insertUser(new User("username", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        User user = userService.getUser("username", "password");
        LoggedInUser.createInstance(user);
        WorkoutService.createInstance(new DatabaseWorkoutRepository());
    }

    @BeforeEach
    void setupEach() {
        DatabaseConnector dbConnector = new DatabaseConnector();
        exerciseSetup();

        model = new WorkoutModel();
    }

    private static void exerciseSetup(){
        Exercise exercise = new Exercise(0, "name", "type", "muscle", "equipment", "difficulty", "instructions");
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        WorkoutService.getInstance().insertExercises(exercises);
    }

    private Workout setupBasicWorkout(){
        List<ExerciseItem> exerciseItems = new ArrayList<>();
        Exercise exercise = model.getExercises().get(0);
        ExerciseItem exerciseItem = new ExerciseItem(exercise);
        exerciseItems.add(exerciseItem);
        exerciseItems.add(exerciseItem);
        Workout workout = new Workout("WorkoutName", exerciseItems);
        return workout;
    }

    @Test
    void testAddExerciseToActiveWorkout() {
        List<ExerciseItem> completedExercises = model.getTodayCompletedExercises();
        Assertions.assertEquals(0, completedExercises.size());

        model.addExerciseToActiveWorkout(new ExerciseItem(model.getExercises().get(0)));
        completedExercises = model.getTodayCompletedExercises();

        ExerciseItem exerciseItem = completedExercises.get(0);
        Assertions.assertNotNull(exerciseItem);
    }

    @Test
    void testSearchExercises() {
        Exercise exercise = model.getExercises().get(0);
        List<Exercise> searchResult = model.searchExercises(exercise.getName());
        String exerciseName = searchResult.get(0).getName();

        Assertions.assertEquals(exercise.getName(), exerciseName);
    }

    @Test
    void testRemoveSet() {
        Exercise exercise = model.getExercises().get(0);
        model.addExerciseToActiveWorkout(new ExerciseItem(exercise));
        ExerciseItem exerciseItem = model.getTodayCompletedExercises().get(0);

        ExerciseItemSet set = new ExerciseItemSet(40, 5);
        exerciseItem.addSet(set);
        Assertions.assertTrue(exerciseItem.getSets().size() > 0);

        model.removeSet(exerciseItem, set);
        exerciseItem = model.getTodayCompletedExercises().get(0);
        Assertions.assertEquals(0, exerciseItem.getSets().size());
    }

    @Test
    void testGetExercises() {
        List<Exercise> exercises = model.getExercises();
        Assertions.assertTrue(exercises.size() != 0);
    }

    @Test
    void testGetTodayCompletedExercises() {
        List<ExerciseItem> exerciseItems = model.getTodayCompletedExercises();
        Assertions.assertEquals(0, exerciseItems.size());

        Exercise exercise = model.getExercises().get(0);
        model.addExerciseToActiveWorkout(new ExerciseItem(exercise));
        exerciseItems = model.getTodayCompletedExercises();

        Assertions.assertTrue(exerciseItems.size() != 0);
    }

    @Test
    void testRemoveCompletedExercise() {
        var exerciseItems = model.getTodayCompletedExercises();
        Assertions.assertEquals(0, exerciseItems.size());

        Exercise exercise = model.getExercises().get(0);
        model.addExerciseToActiveWorkout(new ExerciseItem(exercise));

        exerciseItems = model.getTodayCompletedExercises();
        Assertions.assertTrue(exerciseItems.size() > 0);

        model.removeCompletedExercise(exerciseItems.get(0));
        exerciseItems = model.getTodayCompletedExercises();

        Assertions.assertEquals(0, exerciseItems.size());
    }

    @Test
    void testUpdateCompletedExercise() {
        Exercise exercise = model.getExercises().get(0);
        model.addExerciseToActiveWorkout(new ExerciseItem(exercise));
        ExerciseItem exerciseItem = model.getTodayCompletedExercises().get(0);
        Assertions.assertEquals(0, exerciseItem.getSets().size());

        exerciseItem = model.getTodayCompletedExercises().get(0);
        exerciseItem.addSet(new ExerciseItemSet(10, 10));
        model.updateCompletedExercise(exerciseItem);

        Assertions.assertTrue(exerciseItem.getSets().size() > 0);
    }

    @Test
    void testAddExercisesFromWorkout() {
        Workout workout = setupBasicWorkout();
        model.addExercisesFromWorkout(workout);
        List<ExerciseItem> exerciseItems = model.getTodayCompletedExercises();
        Assertions.assertTrue(exerciseItems.size() > 1);
    }

    @Test
    void testRemoveWorkout() {
        Workout basicWorkout = setupBasicWorkout();
        model.addWorkout(basicWorkout);

        Workout workout = model.getSavedWorkouts().get(0);
        Assertions.assertNotNull(workout);

        model.removeWorkout(workout);
        Assertions.assertEquals(0, model.getSavedWorkouts().size());
    }

    @Test
    void testGetSavedWorkouts() {
        Workout basicWorkout = setupBasicWorkout();
        model.addWorkout(basicWorkout);
        List<Workout> workouts = model.getSavedWorkouts();
        Assertions.assertTrue(workouts.size() != 0);
    }

    @Test
    void testAddWorkout() {
        Workout basicWorkout = setupBasicWorkout();
        model.addWorkout(basicWorkout);
        var workoutName = basicWorkout.getWorkoutName();

        List<Workout> workouts = model.getSavedWorkouts();
        Assertions.assertEquals(workoutName, workouts.get(0).getWorkoutName());
    }
}

