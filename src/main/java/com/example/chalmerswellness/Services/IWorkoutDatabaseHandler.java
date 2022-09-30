package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.List;

public interface IWorkoutDatabaseHandler {
    Exercise insertExerciseItem(Exercise exercise);
    Exercise insertCompletedExercise(Exercise exercise);
    List<Workout> getWorkouts();
    void insertWorkout(Workout workout);
    void removeAddedExercise(Exercise exercise);
    void insertExerciseSet(ExerciseItemSet set);
    List<Exercise> getTodayAddedExercises();
    void removeSet(int setId);
    List<ExerciseItemSet> getExerciseSets(int exerciseItemId);
    List<Exercise> getMyExercises();
    void insertSets(int exerciseId, List<ExerciseItemSet> sets);
    void removeExerciseItem(Exercise exercise);
}
