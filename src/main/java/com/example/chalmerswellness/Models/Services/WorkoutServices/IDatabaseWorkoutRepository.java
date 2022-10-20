package com.example.chalmerswellness.Models.Services.WorkoutServices;

import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.Workout;

import java.time.LocalDate;
import java.util.List;

public interface IDatabaseWorkoutRepository {
    List<Exercise> getExercises();
    List<ExerciseItem> getCompletedExercises();
    List<ExerciseItem> getCompletedExercises(LocalDate date, int userId);
    List<Workout> getWorkouts();
    ExerciseItem insertCompletedExercise(ExerciseItem exercise);
    void insertCompletedExercises(List<ExerciseItem> exercises);
    void insertWorkout(Workout workout);
    void insertExercises(List<Exercise> exercises);
    void updateCompletedExercise(ExerciseItem exerciseItem);
    void deleteCompletedExercise(ExerciseItem exercise);
    void deleteSavedWorkout(Workout workout);
}
