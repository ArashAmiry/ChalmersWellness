package com.example.chalmerswellness.ObjectModels;

import java.util.ArrayList;
import java.util.List;

public class Workout {
    private String workoutName;
    private List<Exercise> exercises = new ArrayList<>();

    public Workout(String name, List<Exercise> exerciseList){
        this.workoutName = name;
        this.exercises = exerciseList;
    }
    public Workout(){
        this.workoutName = "Empty";
    }

    public String getWorkoutName(){
        return workoutName;
    }

    public List<Exercise> getExercises(){
        return exercises;
    }
}
