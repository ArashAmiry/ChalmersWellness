package com.example.chalmerswellness.Services.NutritionServices;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.util.List;

public class NutritionService {
    public enum RepositoryType{
        Database,
        MockDatabase
    }

    private IDatabaseNutritionRepository repository;
    private NutritionService(RepositoryType repositoryType)
    {
        switch (repositoryType){
            case Database -> repository = new DatabaseNutritionRepository();
            //case MockDatabase -> repository = new MemoryRepository();
        }
    }

    private static NutritionService single_instance = null;

    public static void createInstance(RepositoryType repositoryType){
        if(single_instance == null){
            single_instance = new NutritionService(repositoryType);
        }
    }

    public static NutritionService getInstance()
    {
        return single_instance;
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
}
