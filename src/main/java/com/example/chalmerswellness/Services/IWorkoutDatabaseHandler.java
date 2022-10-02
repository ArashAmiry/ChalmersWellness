package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.List;

public interface IWorkoutDatabaseHandler {
    List<ExerciseItem> getCompletedExercises();
    List<ExerciseItemSet> getCompletedSets(int exerciseItemId);
    ExerciseItem insertCompletedExercise(ExerciseItem exercise);
    void insertCompletedSet(ExerciseItemSet set);
    void removeCompletedExercise(ExerciseItem exercise);
    void removeSet(int setId);
    List<Exercise> getExercises();
    void insertCompletedSets(int exerciseId, List<ExerciseItemSet> sets);
    void insertWorkout(Workout workout);
    List<Workout> getWorkouts();
    void insertCompletedExercises(List<ExerciseItem> exercises);

    void updateCompletedExerciseSets(ExerciseItem exerciseItem);

    void updateCompletedExercise(ExerciseItem exerciseItem);
}
