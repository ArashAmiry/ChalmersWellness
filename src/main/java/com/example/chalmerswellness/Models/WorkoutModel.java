package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.Controllers.Workout.States.WorkoutState;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Services.IWorkoutDatabaseHandler;
import com.example.chalmerswellness.Services.WorkoutService;
import com.example.chalmerswellness.Services.testImplement.WorkoutServiceTest;

import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<Observer> observers = new ArrayList<>();
    //private final IWorkoutDatabaseHandler workoutService;
    private WorkoutServiceTest workoutService;
    private List<Exercise> exercises = new ArrayList<>();
    private List<ExerciseItem> addedWorkoutExercises = new ArrayList<>();
    private WorkoutState state;

    public WorkoutModel(){
        workoutService = WorkoutServiceTest.getInstance();
        exercises = getMyExercises();
    }

    public void changeState(WorkoutState state){
        this.state = state;
    }

    public List<Workout> getSavedWorkouts(){
        return workoutService.getWorkouts();
    }

    public List<ExerciseItem> getAddedWorkoutExercises() {
        return addedWorkoutExercises;
    }


    public void addExercise(Exercise exercise){
        state.addExercise(exercise);
    }

    public void addExerciseToActiveWorkout(Exercise exercise){
        ExerciseItem newExerciseItem = new ExerciseItem(exercise);
        workoutService.insertCompletedExercise(newExerciseItem);
        notifyObservers();
    }

    public void addExerciseToWorkout(Exercise exercise){
        ExerciseItem newExerciseItem = new ExerciseItem(exercise);
        addedWorkoutExercises.add(newExerciseItem);
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

    public void updateCompletedExercise(ExerciseItem exerciseItem){
        workoutService.updateCompletedExercise(exerciseItem);
        notifyObservers();
    }

    public void removeSet(ExerciseItem exerciseItem, ExerciseItemSet set){
        exerciseItem.getSets().remove(set);
        //workoutService.removeSet(set);
        updateCompletedExercise(exerciseItem);

        notifyObservers();
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
        List<ExerciseItem> exerciseItems = workout.getExercises();
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
