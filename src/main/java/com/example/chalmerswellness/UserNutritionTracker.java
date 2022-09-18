package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.FoodNutritionModel;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserNutritionTracker {
    List<Pair<Integer, FoodNutritionModel>> meals = new ArrayList<>();

    public void addMeal(int mealId, FoodNutritionModel food){
        Pair<Integer, FoodNutritionModel> meal = new Pair<>(mealId, food);
        meals.add(meal);
    }

    public void removeMeal(FoodNutritionModel food){
         meals.removeIf(meal -> meal.getValue().getId() == food.getId());
    }

}
