package com.example.chalmerswellness.Models.Services.WorkoutServices;

import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.Workout;

import java.time.LocalDate;
import java.util.List;

public final class WorkoutService {
    private static WorkoutService workoutService;
    private final IDatabaseWorkoutRepository repository;
    private WorkoutService(IDatabaseWorkoutRepository workoutRepository){
        repository = workoutRepository;
    }

    public static void createInstance(IDatabaseWorkoutRepository workoutRepository){
        if(workoutService == null){
            workoutService = new WorkoutService(workoutRepository);
        }
    }

    public static WorkoutService getInstance(){
        return workoutService;
    }

    public List<ExerciseItem> getCompletedExercises(){
        return repository.getCompletedExercises();
    }

    public List<ExerciseItem> getCompletedExercises(LocalDate date, int userId){
        return repository.getCompletedExercises(date, userId);
    }

    public ExerciseItem insertCompletedExercise(ExerciseItem exerciseItem){
        return repository.insertCompletedExercise(exerciseItem);
    }

    public void removeCompletedExercise(ExerciseItem exerciseItem){
        repository.deleteCompletedExercise(exerciseItem);
    }

    public List<Exercise> getExercises(){
        return repository.getExercises();
    }

    public void insertWorkout(Workout workout) {
        repository.insertWorkout(workout);
    }

    public List<Workout> getWorkouts() {
        return repository.getWorkouts();
    }

    public void insertCompletedExercises(List<ExerciseItem> exerciseItems) {
        repository.insertCompletedExercises(exerciseItems);
    }

    public void updateCompletedExercise(ExerciseItem exerciseItem) {
        repository.updateCompletedExercise(exerciseItem);
    }

    public void insertExercises(List<Exercise> exercises) {
        repository.insertExercises(exercises);
    }

    public void deleteSavedWorkout(Workout workout){
        repository.deleteSavedWorkout(workout);
    }
}
