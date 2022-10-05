package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.Controllers.Workout.WorkoutStates;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
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
    private List<Exercise> exercises = new ArrayList<>();
    private List<ExerciseItemSet> sets = new ArrayList<>();
    private List<ExerciseItem> addedWorkoutExercises = new ArrayList<>();

    public WorkoutModel(){
        workoutService = new WorkoutService();

        exercises = getMyExercises();
    }

    public List<Workout> getSavedWorkouts(){
        return workoutService.getWorkouts();
    }


    //TODO remove
    /*public List<Exercise> getAddedExercises() {
        return addedExercises;
    }*/


    public List<ExerciseItem> getAddedWorkoutExercises() {
        return addedWorkoutExercises;
    }
    public void addExercise(Exercise exercise, WorkoutStates workoutState){
        ExerciseItem newExerciseItem = new ExerciseItem(exercise);

        //TODO ENUM STATES
        if(workoutState.equals(WorkoutStates.ACTIVEWORKOUT)){
            ExerciseItem exerciseItem = workoutService.insertCompletedExercise(newExerciseItem);

            //TODO remove
            addedExercises.add(exerciseItem);

        } else if(workoutState.equals(WorkoutStates.CREATEWORKOUT)){
            addedWorkoutExercises.add(newExerciseItem);
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

    public void addSet(ExerciseItem exerciseItem){
        workoutService.updateCompletedExerciseSets(exerciseItem);
        notifyObservers();
    }

    public void updateCompletedExercise(ExerciseItem exerciseItem){
        workoutService.updateCompletedExercise(exerciseItem);
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

    public List<ExerciseItem> getTodayCompletedExercises(){
        List<ExerciseItem> completedExercises = workoutService.getCompletedExercises();
        return sortCompletedExercises(completedExercises);
    }

    private List<ExerciseItem> sortCompletedExercises(List<ExerciseItem> exercises){
        List<ExerciseItem> sortedList = new ArrayList<>();
        List<ExerciseItem> completedExercises = new ArrayList<>();

        for (ExerciseItem exerciseItem: exercises) {
            if(exerciseItem.getIsDone()){
                completedExercises.add(0,exerciseItem);
            }else{
                sortedList.add(0, exerciseItem);
            }
        }

        sortedList.addAll(completedExercises);
        return sortedList;
    }

    public void removeExercise(ExerciseItem exerciseItem){
        workoutService.removeCompletedExercise(exerciseItem);
        addedExercises.remove(exerciseItem);
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
        //List<ExerciseItem> exerciseItems = new ArrayList<>();
        List<ExerciseItem> exerciseItems = workout.getExercises();

        /*for (Exercise exercise: workout.getExercises()) {

            ExerciseItem exerciseItem = new ExerciseItem(exercise);
            exerciseItems.add(exerciseItem);
        }
         */

        workoutService.insertCompletedExercises(exerciseItems);

        getTodayCompletedExercises();
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
