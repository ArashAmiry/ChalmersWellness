package com.example.chalmerswellness.Services.testImplement;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.User;

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
    public List<ExerciseItemSet> getCompletedSets(int exerciseItemId)
    {
        return repository.getCompletedSets(exerciseItemId);
    }
    public ExerciseItem insertCompletedExercise(ExerciseItem exerciseItem) {
        return repository.insertCompletedExercise(exerciseItem);
    }
    public void insertCompletedSet(ExerciseItemSet set)
    {
        repository.insertCompletedSet(set);
    }
    public void removeCompletedExercise(ExerciseItem exerciseItem)
    {
        repository.removeCompletedExercise(exerciseItem);
    }
    public void removeSet(int setId)
    {
        repository.removeSet(setId);
    }
    public List<Exercise> getExercises()
    {
        return repository.getExercises();
    }
    public void insertCompletedSets(int exerciseId, List<ExerciseItemSet> sets) {
        repository.insertCompletedSets(exerciseId, sets);
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
    public void updateCompletedExerciseSets(ExerciseItem exerciseItem) {
        repository.updateCompletedExerciseSets(exerciseItem);
    }
    public void updateCompletedExercise(ExerciseItem exerciseItem) {
        repository.updateCompletedExercise(exerciseItem);
    }

}
