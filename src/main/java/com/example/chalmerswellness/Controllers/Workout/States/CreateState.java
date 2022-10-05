package com.example.chalmerswellness.Controllers.Workout.States;

import com.example.chalmerswellness.Models.WorkoutModel;
import com.example.chalmerswellness.ObjectModels.Exercise;

public class CreateState extends WorkoutState{

    public CreateState(WorkoutModel model) {
        super(model);
    }

    @Override
    public void addExercise(Exercise exercise) {
        model.addExerciseToWorkout(exercise);
    }
}
