package com.example.chalmerswellness.Services.WorkoutServices;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class WorkoutService {
    private static WorkoutService single_instance = null;
    private IDatabaseWorkoutRepository repository;
    private WorkoutService(IDatabaseWorkoutRepository workoutRepository)
    {
        repository = workoutRepository;
    }

    public static void createInstance(IDatabaseWorkoutRepository workoutRepository){
        if(single_instance == null){
            single_instance = new WorkoutService(workoutRepository);
        }
    }

    public static WorkoutService getInstance()
    {
        return single_instance;
    }

    public List<ExerciseItem> getCompletedExercises()
    {
        return repository.getCompletedExercises();
    }

    public List<ExerciseItem> getCompletedExercises(LocalDate date, int userId)
    {
        return repository.getCompletedExercises(date, userId);
    }

    public ExerciseItem insertCompletedExercise(ExerciseItem exerciseItem) {
        return repository.insertCompletedExercise(exerciseItem);
    }

    public void removeCompletedExercise(ExerciseItem exerciseItem)
    {
        repository.deleteCompletedExercise(exerciseItem);
    }

    public List<Exercise> getExercises()
    {
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

    public void removeWorkout(Workout workout) {
        repository.deleteWorkout(workout);
    }
}
