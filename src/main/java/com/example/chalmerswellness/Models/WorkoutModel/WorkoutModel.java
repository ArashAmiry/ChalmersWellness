package com.example.chalmerswellness.Models.WorkoutModel;

import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.Models.ObjectModels.Workout;
import com.example.chalmerswellness.Interfaces.Observable;
import com.example.chalmerswellness.Interfaces.Observer;
import com.example.chalmerswellness.Models.Services.WorkoutServices.WorkoutService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkoutModel implements Observable {
    private List<Observer> observers = new ArrayList<>();
    private WorkoutService workoutService = WorkoutService.getInstance();
    private List<Exercise> exercises;

    public WorkoutModel(){
        //workoutService = WorkoutService.getInstance();
        exercises = getExercises();
    }

    public List<Workout> getSavedWorkouts(){
        return workoutService.getWorkouts();
    }

    /*public List<ExerciseItem> getAddedWorkoutExercises() {
        return addedWorkoutExercises;
    }*/

    /**
     * This method inserts a exerciseItem to the database.
     * <p>
     * @param  exercise  an exercise that will be used to insert to database
     */
    public void addExerciseToActiveWorkout(ExerciseItem exercise){
        workoutService.insertCompletedExercise(exercise);
        notifyObservers();
    }

    /*public void addExerciseToWorkout(Exercise exercise){
        ExerciseItem newExerciseItem = new ExerciseItem(exercise);
        addedWorkoutExercises.add(newExerciseItem);
        notifyObservers();
    }*/

    /**
     * This method searches through a list of exercises to find matching exercises with similar names.
     * <p>
     * @param exerciseName
     * @return List<Exercise> This returns a list of exercises
     */
    public List<Exercise> searchExercises(String exerciseName){
        List<Exercise> searchResult = new ArrayList<>();
        String searchString = exerciseName.toLowerCase(Locale.ROOT).replaceAll("\\s+","");
        for (Exercise exercise: exercises) {
            if(exercise.getName().toLowerCase(Locale.ROOT).replaceAll("\\s+","").contains(searchString))
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
        updateCompletedExercise(exerciseItem);
        notifyObservers();
    }

    /**
     * This method is used to get all the exercises that is stored in the workoutService repository.
     * <p>
     * @return List<Exercise> This returns a list of all exercises from the service.
     */
    public List<Exercise> getExercises(){
        return workoutService.getExercises();
    }

    /**
     * This method is used to get all the completed exercises for the day in a sorted list.
     * <p>
     * @return List<ExerciseItem> This returns a list of sorted exerciseItems for the day.
     */
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
            if(exerciseItem.isDone()){
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
        //addedWorkoutExercises.clear();
        workoutService.insertWorkout(workout);
        notifyObservers();
    }

    public void removeWorkout(Workout workout){
        workoutService.deleteSavedWorkout(workout);
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

    /*public void removeAllWorkoutExercises(){
        addedWorkoutExercises.clear();
        notifyObservers();
    }*/

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
