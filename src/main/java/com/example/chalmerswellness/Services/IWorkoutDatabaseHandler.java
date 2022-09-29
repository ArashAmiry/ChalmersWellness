package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;

import java.util.List;

public interface IWorkoutDatabaseHandler {
    Exercise insertExerciseItem(Exercise exercise);
    Exercise insertCompletedExercise(Exercise exercise);
    void removeAddedExercise(Exercise exercise);
    void insertExerciseSet(ExerciseItemSet set);
    List<Exercise> getTodayAddedExercises();
    void removeSet(int setId);
    List<ExerciseItemSet> getExerciseSets(int exerciseItemId);
    List<Exercise> getMyExercises();
    void insertSets(int exerciseId, List<ExerciseItemSet> sets);
    void removeExerciseItem(Exercise exercise);
}
