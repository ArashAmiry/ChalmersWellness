package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;

import java.util.List;

public interface IWorkoutDatabaseHandler {
/*
    Exercise insertExerciseItem(Exercise exercise);
*/
    List<Exercise> getCompletedExercises();
    List<ExerciseItemSet> getCompletedSets(int exerciseItemId);

    Exercise insertCompletedExercise(Exercise exercise);
    void insertCompletedSet(ExerciseItemSet set);
    void removeCompletedExercise(Exercise exercise);

    //TODO is this being used
    void removeSet(int setId);


    //List<Workout> getWorkouts();
    //void insertWorkout(Workout workout);
    List<Exercise> getMyExercises();
    void insertCompletedSets(int exerciseId, List<ExerciseItemSet> sets);
    //void removeExerciseItem(Exercise exercise);




}
