package com.example.chalmerswellness.Services.testImplement;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.List;

public class WorkoutServiceTest {

    public enum RepositoryType{
        Database,
        MockDatabase
    }

    private IDatabaseWorkoutRepository repository;
    public WorkoutServiceTest(RepositoryType repositoryType)
    {
        switch (repositoryType){
            case Database -> repository = new DatabaseRepository();
            case MockDatabase -> repository = new MemoryRepository();
        }
    }

    private static WorkoutServiceTest single_instance = null;

    public static void createInstance(WorkoutServiceTest serviceTest){
        if(single_instance == null){
            single_instance = serviceTest;
        }
    }

    public static WorkoutServiceTest getInstance()
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
