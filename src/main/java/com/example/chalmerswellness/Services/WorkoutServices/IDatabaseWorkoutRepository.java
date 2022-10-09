package com.example.chalmerswellness.Services.WorkoutServices;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.List;

public interface IDatabaseWorkoutRepository {
    List<Exercise> getExercises();
    List<ExerciseItem> getCompletedExercises();
    List<Workout> getWorkouts();
    ExerciseItem insertCompletedExercise(ExerciseItem exercise);
    void insertCompletedExercises(List<ExerciseItem> exercises);
    void insertWorkout(Workout workout);
    void updateCompletedExercise(ExerciseItem exerciseItem);
    void deleteCompletedExercise(ExerciseItem exercise);
}
