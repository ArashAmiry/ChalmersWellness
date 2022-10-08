package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.Controllers.Workout.States.WorkoutState;
import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Services.IWorkoutDatabaseHandler;
import com.example.chalmerswellness.Services.WorkoutService;
import java.util.ArrayList;
import java.util.List;

public class WorkoutModel implements Observable {
    private List<Observer> observers = new ArrayList<>();
    private final IWorkoutDatabaseHandler workoutService;
    private List<Exercise> exercises = new ArrayList<>();
    private List<ExerciseItem> addedWorkoutExercises = new ArrayList<>();
    private WorkoutState state;

    public WorkoutModel(){
        workoutService = new WorkoutService();
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

    /**
     * This method inserts a exerciseItem to the database.
     * <p>
     * @param  exercise  an exercise that will be used to insert to database
     */
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

    /**
     * This method searches through a list of exercises to find matching exercises with similar names.
     * <p>
     * @param exerciseName
     * @return List<Exercise> This returns a list of exercises
     */
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

    public void removeSet(int setId){
        workoutService.removeSet(setId);
        notifyObservers();
    }

    public List<Exercise> getMyExercises(){
        return workoutService.getExercises();
    }

    public List<ExerciseItem> getTodayCompletedExercises(){
        List<ExerciseItem> completedExercises = workoutService.getCompletedExercises();
        return sortCompletedExercises(completedExercises);
    }

    /**
     * This method is used to sort a list where all exerciseItems that are done
     * is placed last in the list.
     * <p>
     * @param exerciseItems
     * @return List<ExerciseItem> This returns a list of sorted exerciseItems
     */
    private List<ExerciseItem> sortCompletedExercises(List<ExerciseItem> exerciseItems){
        List<ExerciseItem> sortedList = new ArrayList<>();
        List<ExerciseItem> completedExercises = new ArrayList<>();

        for (ExerciseItem exerciseItem: exerciseItems) {
            if(exerciseItem.getIsDone()){
                completedExercises.add(0,exerciseItem);
            }else{
                sortedList.add(0, exerciseItem);
            }
        }

        sortedList.addAll(completedExercises);
        return sortedList;
    }

    /**
     * This method removes a completedExercise
     * <p>
     * @param exerciseItem
     */
    public void removeCompletedExercise(ExerciseItem exerciseItem){
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

    /**
     * This method adds all exerciseItems that the workout contains into today's workout.
     * <p>
     * @param workout
     */
    public void addExercisesFromWorkout(Workout workout){
        List<ExerciseItem> exerciseItems = workout.getExercises();
        workoutService.insertCompletedExercises(exerciseItems);
        //getTodayCompletedExercises();
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
