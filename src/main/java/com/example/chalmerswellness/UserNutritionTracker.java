package com.example.chalmerswellness;

import com.example.chalmerswellness.calorieAPI.Food;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class UserNutritionTracker {
    List<Pair<Integer, Food>> meals = new ArrayList<>();

    public void addMeal(int mealId, Food food){
        Pair<Integer, Food> meal = new Pair<>(mealId, food);
        meals.add(meal);
    }

    /*public void removeMeal(Food food){
         meals.removeIf(meal -> meal.getValue().getId() == food.getId());
    }*/

}
