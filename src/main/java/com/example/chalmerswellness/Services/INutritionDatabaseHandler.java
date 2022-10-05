package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.util.List;

public interface INutritionDatabaseHandler {

    void insertNutrition(Food nutritionModel, Meal meal);

    void removeNutrition(int foodId);

    List<Food> getTodaysNutrition(Meal meal);


}
