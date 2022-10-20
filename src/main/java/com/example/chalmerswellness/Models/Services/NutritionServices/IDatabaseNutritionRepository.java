package com.example.chalmerswellness.Models.Services.NutritionServices;

import com.example.chalmerswellness.Models.FoodModel.Food;
import com.example.chalmerswellness.Enums.Meal;

import java.util.List;

public interface IDatabaseNutritionRepository {

    void insertNutrition(Food nutritionModel, Meal meal);

    void removeNutrition(int foodId);

    List<Food> getTodaysNutrition(Meal meal);
    double getTodaysProtein();

    double getTodaysFat();

    double getTodaysCarbs();
}
