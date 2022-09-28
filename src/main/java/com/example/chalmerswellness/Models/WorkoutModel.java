package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Services.WorkoutService;

import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<Exercise> addedExercises = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    //private final DataService db;
    private List<ExerciseItemSet> sets = new ArrayList<>();
    private List<Exercise> exercises = new ArrayList<>();

    public WorkoutModel(){
        //db = new DataService();
        //db = DataService.getInstance();


        exercises = getMyExercises();
    }

    public List<Exercise> getAddedExercises() {
        return addedExercises;
    }

    public void addExercise(Exercise exercise){
       var exerciseItem = WorkoutService.insertExerciseItem(exercise);
        addedExercises.add(exerciseItem);
        notifyObservers();
    }

    public List<Exercise> searchExercises(String exerciseName){

        List<Exercise> searchResult = new ArrayList<>();
        for (var exercise: exercises) {
            if(exercise.getName().toLowerCase().replaceAll("\\s+","").contains(exerciseName))
                searchResult.add(exercise);
        }
        return searchResult;
    }

    public void getTodaysExercises(){
        var exerciseItems = getTodayExerciseItems();
        addedExercises.addAll(exerciseItems);
        notifyObservers();
    }

    public void addSet(ExerciseItemSet set){
        sets.add(set);
        WorkoutService.insertExerciseSet(set);
        notifyObservers();
    }

    public void saveSets(int setId){
        WorkoutService.insertSets(setId, sets);
    }

    public void removeSet(int setId){
        WorkoutService.removeSet(setId);
        notifyObservers();
    }

    public List<ExerciseItemSet> getSets(int exerciseItemId){
        sets = WorkoutService.getExerciseSets(exerciseItemId);
        return sets;
    }

    public List<Exercise> getMyExercises(){
        return WorkoutService.getMyExercises();
    }

    public List<Exercise> getTodayExerciseItems(){
        return WorkoutService.getTodayExerciseItems();
    }
    public void removeExercise(Exercise exercise){
        WorkoutService.removeExerciseItem(exercise);
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
