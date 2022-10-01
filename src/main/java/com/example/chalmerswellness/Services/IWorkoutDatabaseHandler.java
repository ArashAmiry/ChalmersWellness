package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.util.List;

public interface IWorkoutDatabaseHandler {
    List<Exercise> getCompletedExercises();
    List<ExerciseItemSet> getCompletedSets(int exerciseItemId);
    Exercise insertCompletedExercise(Exercise exercise);
    void insertCompletedSet(ExerciseItemSet set);
    void removeCompletedExercise(Exercise exercise);

    //TODO is this being used
    void removeSet(int setId);

    List<Exercise> getMyExercises();
    void insertCompletedSets(int exerciseId, List<ExerciseItemSet> sets);
    void insertWorkout(Workout workout);
    List<Workout> getWorkouts();

    void insertCompletedExercises(List<Exercise> exercises);

    //Exercise insertWorkoutExercise(Exercise exercise);
    //void removeExerciseItem(Exercise exercise);


}
