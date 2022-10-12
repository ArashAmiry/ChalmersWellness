package com.example.chalmerswellness.ObjectModels;


import java.util.ArrayList;
import java.util.List;

public class Workout {
    private int id;
    private String workoutName;
    private List<ExerciseItem> exercises = new ArrayList<>();

    public Workout(String name, List<ExerciseItem> exerciseList){
        this.workoutName = name;
        this.exercises = exerciseList;
    }
    public Workout(){
    }

    public void addExercise(Exercise exercise){
        ExerciseItem newExerciseItem = new ExerciseItem(exercise);
        exercises.add(newExerciseItem);
    }

    public void removeExercise(ExerciseItem exercise){
        exercises.remove(exercise);
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public List<ExerciseItem> getExercises() {
        return exercises;
    }
}
