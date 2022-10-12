package com.example.chalmerswellness;

import com.example.chalmerswellness.Services.DbConnectionService;
import com.example.chalmerswellness.Services.NutritionServices.DatabaseNutritionRepository;
import com.example.chalmerswellness.Services.NutritionServices.NutritionService;
import com.example.chalmerswellness.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Services.UserServices.UserService;
import com.example.chalmerswellness.calorieAPI.FoodFacade;
import com.example.chalmerswellness.calorieAPI.Meal;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class NutritionServiceTest {

    private static NutritionService nutritionService;
    FoodFacade foodFacade = new FoodFacade();

    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        UserService.createInstance(new DatabaseUserRepository());
        UserService.getInstance().deleteAllUsers();
        UserService.getInstance().insertUser(new User("username", "password", "firstName", "lastName", Gender.Male, "email", LocalDate.now(),1, 1));
        LoggedInUser.createInstance(UserService.getInstance().getUser("username", "password"));
        NutritionService.createInstance(new DatabaseNutritionRepository());
        nutritionService = NutritionService.getInstance();
    }

    @BeforeEach
    void setupEach() throws JsonProcessingException {
        nutritionService.deleteNutritionData();
        nutritionService.insertNutrition(foodFacade.createFood("Apple"), Meal.BREAKFAST);
    }

    @Test
    void insertNutritionMethodShouldInsertNutrition() {
        Assertions.assertTrue(nutritionService.getTodaysNutrition(Meal.BREAKFAST).size() == 1);
    }

    @Test
    void removeNutritionShouldRemoveNutrition() {
        nutritionService.removeNutrition(1);
        Assertions.assertTrue(nutritionService.getTodaysNutrition(Meal.BREAKFAST).size() == 0);
    }

    @Test
    void deleteNutritionDataShouldDeleteNutritionData() {
        nutritionService.deleteNutritionData();
        Assertions.assertTrue(nutritionService.getTodaysNutrition(Meal.BREAKFAST).size() == 0);
    }
}