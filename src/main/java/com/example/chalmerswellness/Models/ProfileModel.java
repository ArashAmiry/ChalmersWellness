package com.example.chalmerswellness.Models;

import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Services.WorkoutServices.WorkoutService;

import java.time.LocalDate;
import java.util.List;

public class ProfileModel {

    WorkoutService workoutService = WorkoutService.getInstance();

    public List<ExerciseItem> getCompletedExercises(LocalDate date, int userId){
        return  workoutService.getCompletedExercises(date, userId);
    }
}
