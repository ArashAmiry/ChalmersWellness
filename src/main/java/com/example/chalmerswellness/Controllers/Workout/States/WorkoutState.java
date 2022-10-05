package com.example.chalmerswellness.Controllers.Workout.States;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;

public abstract class WorkoutState {
    protected WorkoutModel model;

    public WorkoutState(WorkoutModel model){
        this.model = model;
    }

    public void addExercise(Exercise exercise) {
        model.addExerciseToActiveWorkout(exercise);
    }
}
