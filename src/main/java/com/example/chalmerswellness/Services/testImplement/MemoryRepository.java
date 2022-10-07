package com.example.chalmerswellness.Services.testImplement;


import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.ArrayList;
import java.util.List;

public class MemoryRepository implements IDatabaseWorkoutRepository {

    private List<Exercise> exercises = new ArrayList<>();
    private List<ExerciseItem> completedExercises = new ArrayList<>();
    private List<ExerciseItemSet> completedSets = new ArrayList<>();
    private List<Workout> workouts = new ArrayList<>();

    public MemoryRepository(){
        exercises.add(new Exercise(0, "Deadlift", "Bodybuilding", "Back", "Barbell", "Medium", "Pull the bar up"));
        exercises.add(new Exercise(1, "BenchPress", "Bodybuilding", "Chest", "Barbell", "Medium", "Pull the bar up"));
        exercises.add(new Exercise(2, "Dumbbell Curls", "Bodybuilding", "Bicep", "Dumbbell", "Easy", "Lift the weight"));
    }

    @Override
    public List<ExerciseItem> getCompletedExercises() {
        return completedExercises;
    }

    @Override
    public List<ExerciseItemSet> getCompletedSets(int exerciseItemId) {
        return completedSets;
    }

    @Override
    public ExerciseItem insertCompletedExercise(ExerciseItem exerciseItem) {
        completedExercises.add(exerciseItem);
        return exerciseItem;
    }

    @Override
    public void insertCompletedSet(ExerciseItemSet set) {

        completedSets.add(set);
    }

    @Override
    public void removeCompletedExercise(ExerciseItem exercise) {
        completedExercises.remove(exercise);
    }

    @Override
    public void removeSet(ExerciseItemSet set) {
        completedSets.remove(set);
    }

    @Override
    public List<Exercise> getExercises() {
        return exercises;
    }

    @Override
    public void insertCompletedSets(int exerciseId, List<ExerciseItemSet> sets) {
        sets.addAll(sets);
    }

    @Override
    public void insertWorkout(Workout workout) {
        workouts.add(workout);
    }

    @Override
    public List<Workout> getWorkouts() {
        return workouts;
    }

    @Override
    public void insertCompletedExercises(List<ExerciseItem> exercises) {
        completedExercises.addAll(exercises);
    }

    @Override
    public void updateCompletedExerciseSets(ExerciseItem exerciseItem) {

        //exerciseItem.addSet(exerciseIte);
        //completedSets.addAll(exerciseItem.getSets());
    }

    @Override
    public void updateCompletedExercise(ExerciseItem exerciseItem) {
        //remove and add again
    }
}


