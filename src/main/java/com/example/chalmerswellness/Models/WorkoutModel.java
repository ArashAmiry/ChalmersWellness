package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.DataService;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Observable;
import com.example.chalmerswellness.Observer;
import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<ExerciseItem> addedExercises = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private DataService db;


    public WorkoutModel(){
        db = new DataService();
    }

    public List<ExerciseItem> getAddedExercises() {
        return addedExercises;
    }

    public void addExercise(Exercise exercise){
        //TODO skapa i databas
        DataService db = new DataService();
        var exerciseItem = db.insertExerciseItem(exercise);

        addedExercises.add(exerciseItem);




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
