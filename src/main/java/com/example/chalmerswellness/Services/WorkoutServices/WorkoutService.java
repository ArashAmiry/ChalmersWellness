package com.example.chalmerswellness.Services.WorkoutServices;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.List;

public class WorkoutService {

    public enum RepositoryType{
        Database,
        MockDatabase
    }

    private IDatabaseWorkoutRepository repository;
    private WorkoutService(RepositoryType repositoryType)
    {
        switch (repositoryType){
            case Database -> repository = new DatabaseWorkoutRepository();
            //case MockDatabase -> repository = new MemoryRepository();
        }
    }

    private static WorkoutService single_instance = null;

    public static void createInstance(RepositoryType repositoryType){
        if(single_instance == null){
            single_instance = new WorkoutService(repositoryType);
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
}
