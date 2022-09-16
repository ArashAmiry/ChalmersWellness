package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.Observable;
import com.example.chalmerswellness.Observer;
import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<Exercise> addedExercises = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();


    public WorkoutModel(){

    }

    public List<Exercise> getAddedExercises() {
        return addedExercises;
    }

    public void addExercise(Exercise exercise){
        addedExercises.add(exercise);
        notifyObservers();
    }

    public void removeExercise(Exercise exercise){
        addedExercises.remove(exercise);
        notifyObservers();
    }


    @Override
    public void notifyObservers() {
        for (var observer: observers) {
            observer.update(this);
        }
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }


}
