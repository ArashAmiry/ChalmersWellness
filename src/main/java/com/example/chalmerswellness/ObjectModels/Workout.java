package com.example.chalmerswellness.ObjectModels;


import java.util.ArrayList;
import java.util.List;

public class Workout {
    //TODO: ID is never set
    private int id;
    private String workoutName;
    private List<ExerciseItem> exercises = new ArrayList<>();

    public Workout(String name, List<ExerciseItem> exerciseList){
        this.workoutName = name;
        this.exercises = exerciseList;
    }
    public Workout(){
    }

    public void addExercise(ExerciseItem exercise){
        exercises.add(exercise);
    }

    public void removeExercise(ExerciseItem exercise){
        exercises.remove(exercise);
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public int getId() {
        return id;
    }



    public List<ExerciseItem> getExercises() {
        return exercises;
    }
}
