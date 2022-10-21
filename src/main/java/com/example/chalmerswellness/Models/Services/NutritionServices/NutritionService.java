package com.example.chalmerswellness.Models.Services.NutritionServices;

import com.example.chalmerswellness.Models.FoodModel.Food;
import com.example.chalmerswellness.Enums.Meal;

import java.util.List;

public final class NutritionService {
    private final IDatabaseNutritionRepository repository;
    private NutritionService(IDatabaseNutritionRepository repositoryType)
    {
        repository = repositoryType;
    }

    private static NutritionService nutritionService = null;

    public static void createInstance(IDatabaseNutritionRepository repositoryType){
        if (nutritionService == null) {
            nutritionService = new NutritionService(repositoryType);
        }
    }

    public static NutritionService getInstance()
    {
        return nutritionService;
    }

    public void insertNutrition(Food nutritionModel, Meal meal) {
        repository.insertNutrition(nutritionModel, meal);
    }

    public void removeNutrition(int foodId) {
        repository.removeNutrition(foodId);
    }

    public List<Food> getTodaysNutrition(Meal meal) {
        return repository.getTodaysNutrition(meal);
    }

    public double getTodaysProtein() {
        return repository.getTodaysProtein();
    }

    public double getTodaysCarbs() {
        return repository.getTodaysCarbs();
    }

    public double getTodaysFat() {
        return repository.getTodaysFat();
    }

}
