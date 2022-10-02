package com.example.chalmerswellness.ObjectModels;

import java.util.ArrayList;
import java.util.List;

public class ExerciseItem extends Exercise{
    private int id;
    private List<ExerciseItemSet> sets = new ArrayList<>();
    private boolean isDone = false;

    public ExerciseItem(Exercise exercise){
        super(exercise.getId(), exercise);
    }

    public ExerciseItem(int id, Exercise exercise){
        super(exercise.getId(), exercise);
        this.id = id;
    }

    public int getExerciseItemId(){
        return this.id;
    }

    public List<ExerciseItemSet> getSets() {
        return sets;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public void addSet(ExerciseItemSet set){
        sets.add(set);
    }

}
