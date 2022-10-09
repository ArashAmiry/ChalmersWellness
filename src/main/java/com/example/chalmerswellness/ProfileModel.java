package com.example.chalmerswellness;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.Services.WorkoutService;

import java.sql.Date;
import java.util.List;

public class ProfileModel {

    WorkoutService workoutService = new WorkoutService();

    public List<Exercise> getCompletedExercises(Date date){
        return  workoutService.getCompletedExercises(date);
    }
}
