package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.Controllers.Workout.WorkoutStates;
import com.example.chalmerswellness.Services.DataService;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<Exercise> addedExercises = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private final DataService db;
    private List<ExerciseItemSet> sets = new ArrayList<>();
    private List<Exercise> exercises = new ArrayList<>();

    private List<Exercise> addedWorkoutExercises = new ArrayList<>();

    public WorkoutModel(){
        db = new DataService();
        exercises = getMyExercises();
    }

    public List<Exercise> getAddedExercises() {
        return addedExercises;
    }
    public List<Exercise> getAddedWorkoutExercises() {
        return addedWorkoutExercises;
    }
    public void addExercise(Exercise exercise, WorkoutStates workoutState){
        //DOES IT STORE IN SAME db?
        var exerciseItem = db.insertExerciseItem(exercise);

        //TODO ENUM STATES
        if(workoutState.equals(WorkoutStates.ACTIVEWORKOUT)){
            addedExercises.add(exerciseItem);
        } else if(workoutState.equals(WorkoutStates.CREATEWORKOUT)){
            addedWorkoutExercises.add(exerciseItem);
        }

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
        db.insertExerciseSet(set);
        notifyObservers();
    }

    public void saveSets(int setId){
        db.insertSets(setId, sets);
    }

    public void removeSet(int setId){
        db.removeSet(setId);
        notifyObservers();
    }

    public List<ExerciseItemSet> getSets(int exerciseItemId){
        sets = db.getExerciseSets(exerciseItemId);
        return sets;
    }

    public List<Exercise> getMyExercises(){
        return db.getMyExercises();
    }

    public List<Exercise> getTodayExerciseItems(){
        return db.getTodayExerciseItems();
    }
    public void removeExercise(Exercise exercise){
        db.removeExerciseItem(exercise);
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
