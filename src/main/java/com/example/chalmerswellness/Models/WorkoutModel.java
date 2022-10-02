package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.Controllers.Workout.WorkoutStates;
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
        //return null;
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
        Exercise exerciseItem;


        //TODO ENUM STATES
        if(workoutState.equals(WorkoutStates.ACTIVEWORKOUT)){
            exerciseItem = workoutService.insertCompletedExercise(exercise);
            addedExercises.add(exerciseItem);
        } else if(workoutState.equals(WorkoutStates.CREATEWORKOUT)){
            addedWorkoutExercises.add(exercise);
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
        workoutService.insertCompletedSet(set);
        notifyObservers();
    }

    public void saveSets(int setId){
        workoutService.insertCompletedSets(setId, sets);
    }

    public void removeSet(int setId){
        workoutService.removeSet(setId);
        notifyObservers();
    }

    public List<ExerciseItemSet> getSets(int exerciseItemId){
        sets = workoutService.getCompletedSets(exerciseItemId);
        return sets;
    }

    public List<Exercise> getMyExercises(){
        return workoutService.getExercises();
    }

    public List<Exercise> getTodayExerciseItems(){
        return workoutService.getCompletedExercises();
    }
    public void removeExercise(Exercise exercise){
        workoutService.removeCompletedExercise(exercise);
        addedExercises.remove(exercise);
        addedWorkoutExercises.remove(exercise);
        notifyObservers();
    }

    public void addWorkout(Workout workout){
        addedWorkoutExercises.clear();
        workoutService.insertWorkout(workout);
        notifyObservers();
    }

    public void removeWorkout(Workout workout){
        //savedWorkouts.remove(workout);
        notifyObservers();
    }

    //TODO show promt if exercises are already added
    public void addExercisesFromWorkout(Workout workout){
        addedExercises.clear();
        workoutService.insertCompletedExercises(workout.getExercises());
        getTodaysExercises();
        notifyObservers();
    }

    public void removeAllWorkoutExercises(){
        addedWorkoutExercises.clear();
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
