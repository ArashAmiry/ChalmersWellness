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
        this.workoutName = "Empty";
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public List<ExerciseItem> getExercises() {
        return exercises;
    }
}
