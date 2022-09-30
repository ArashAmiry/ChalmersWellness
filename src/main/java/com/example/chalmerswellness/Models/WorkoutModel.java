package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.Controllers.Workout.WorkoutStates;
import com.example.chalmerswellness.Services.DataService;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Services.IWorkoutDatabaseHandler;
import com.example.chalmerswellness.Services.WorkoutService;
import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<Exercise> addedExercises = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private final IWorkoutDatabaseHandler workoutService;
    private List<ExerciseItemSet> sets = new ArrayList<>();
    private List<Exercise> exercises = new ArrayList<>();

    private List<Exercise> addedWorkoutExercises = new ArrayList<>();

    public WorkoutModel(){
        workoutService = new WorkoutService();

        exercises = getMyExercises();
    }

    public List<Workout> getSavedWorkouts(){
        return workoutService.getWorkouts();
    }

    public List<Exercise> getAddedExercises() {
        return addedExercises;
    }
    public List<Exercise> getAddedWorkoutExercises() {
        return addedWorkoutExercises;
    }
    public void addExercise(Exercise exercise, WorkoutStates workoutState){
        //DOES IT STORE IN SAME db?
        var exerciseItem = workoutService.insertExerciseItem(exercise);

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
        workoutService.insertExerciseSet(set);
        notifyObservers();
    }

    public void saveSets(int setId){
        workoutService.insertSets(setId, sets);
    }

    public void removeSet(int setId){
        workoutService.removeSet(setId);
        notifyObservers();
    }

    public List<ExerciseItemSet> getSets(int exerciseItemId){
        sets = workoutService.getExerciseSets(exerciseItemId);
        return sets;
    }

    public List<Exercise> getMyExercises(){
        return workoutService.getMyExercises();
    }

    public List<Exercise> getTodayExerciseItems(){
        return workoutService.getTodayAddedExercises();
    }
    public void removeExercise(Exercise exercise){
        workoutService.removeAddedExercise(exercise);
        addedExercises.remove(exercise);
        notifyObservers();
    }

    public void addWorkout(Workout workout){
        dataService.insertWorkout(workout);
        //notifyObservers();
    }

    public void removeWorkout(Workout workout){
        //savedWorkouts.remove(workout);
        notifyObservers();
    }

    public void removeAllExercises(){
        addedExercises.clear();
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
