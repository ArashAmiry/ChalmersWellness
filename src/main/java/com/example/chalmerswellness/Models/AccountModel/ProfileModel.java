package com.example.chalmerswellness.Models.AccountModel;

import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.Services.WorkoutServices.WorkoutService;

import java.time.LocalDate;
import java.util.List;

public class ProfileModel {

    private WorkoutService workoutService = WorkoutService.getInstance();

    public List<ExerciseItem> getCompletedExercises(LocalDate date, int userId){
        return  workoutService.getCompletedExercises(date, userId);
    }
}
