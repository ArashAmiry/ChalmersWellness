package com.example.chalmerswellness.ObjectModels;

public class ExerciseItemSet {
    private double weight;
    private int reps;

    public ExerciseItemSet(double weight, int reps){
        this.weight = weight;
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public int getReps() {
        return reps;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

}
