package com.example.chalmerswellness.ObjectModels;

public class ExerciseItemSet {
    private int id;
    private double weight;
    private int reps;

    public ExerciseItemSet(int id, double weight, int reps){
        this.id = id;
        this.weight = weight;
        this.reps = reps;
    }

    public int getId() {
        return id;
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
