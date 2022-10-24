package com.example.chalmerswellness;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Enums.Meal;
import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.FoodModel.FoodFacade;
import com.example.chalmerswellness.Models.ObjectModels.User;
import com.example.chalmerswellness.Models.Services.DatabaseConnector;
import com.example.chalmerswellness.Models.Services.DbConnectionService;
import com.example.chalmerswellness.Models.Services.NutritionServices.DatabaseNutritionRepository;
import com.example.chalmerswellness.Models.Services.NutritionServices.NutritionService;
import com.example.chalmerswellness.Models.Services.UserServices.DatabaseUserRepository;
import com.example.chalmerswellness.Models.Services.UserServices.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class FoodFacadeTest {
    private static FoodFacade foodFacade;
    private static NutritionService nutritionService;
    private static UserService userService;
    private static User loggedInUser;
    @BeforeAll
    static void setup() {
        DbConnectionService.createInstance(false);
        NutritionService.createInstance(new DatabaseNutritionRepository());
        UserService.createInstance(new DatabaseUserRepository());
    }

    @BeforeEach
    void setupEach() throws JsonProcessingException {
        DatabaseConnector dbConnector = new DatabaseConnector();
        foodFacade = new FoodFacade();
        userService = UserService.getInstance();
        userService.insertUser(new User("username", "password", "firstName", "lastName", Gender.MALE, "email", LocalDate.now(),1, 1));
        LoggedInUser.createInstance(UserService.getInstance().getUser("username", "password"));
        loggedInUser = LoggedInUser.getInstance();
        nutritionService = NutritionService.getInstance();
        nutritionService.insertNutrition(foodFacade.createFood("Apple"), Meal.BREAKFAST);
    }

    @Test
    void createFoodMethodShouldReturnFoodObject() throws JsonProcessingException {
        Assertions.assertNotNull(foodFacade.createFood("Apple"));
    }

    @Test
    void getConsumedProteinTodayShouldReturnProteinConsumedToday() throws JsonProcessingException {
        Assertions.assertTrue(foodFacade.getConsumedProteinToday() > 0);
    }

    @Test
    void getConsumedCarbsTodayShouldReturnCarbsConsumedToday() throws JsonProcessingException {
        Assertions.assertTrue(foodFacade.getConsumedCarbsToday() > 0);
    }

    @Test
    void getConsumedFatTodayShouldReturnFatConsumedToday() throws JsonProcessingException {
        Assertions.assertTrue(foodFacade.getConsumedFatToday() > 0);
    }

    @Test
    void isFoodExistingShouldReturnTrueIfFoodExists() throws JsonProcessingException {
        Assertions.assertTrue(foodFacade.isFoodExisting("Apple"));
    }

    @Test
    void removeFoodShouldRemoveFoodFromDatabase() throws JsonProcessingException {
        int appleId = nutritionService.getTodaysNutrition(Meal.BREAKFAST).get(0).getId();
        foodFacade.removeFood(appleId);
        Assertions.assertEquals(0, nutritionService.getTodaysNutrition(Meal.BREAKFAST).size());
    }

    @Test
    void validateAmountOfGramsShouldReturnTrueIfAmountIsPositive() {
        Assertions.assertTrue(foodFacade.validateAmountOfGrams(String.valueOf(1)));
    }

    @Test
    void validateAmountOfGramsShouldReturnFalseIfAmountIsNegative() {
        Assertions.assertFalse(foodFacade.validateAmountOfGrams(String.valueOf(-1)));
    }

    @Test
    void getTodaysCaloriesShouldReturnCaloriesConsumedToday() throws JsonProcessingException {
        Assertions.assertTrue(foodFacade.getTodaysCalories() > 0);
    }

    @Test
    void caloriesLeftTodayShouldReturnCaloriesLeftToday() throws JsonProcessingException {
        nutritionService.removeNutrition(nutritionService.getTodaysNutrition(Meal.BREAKFAST).get(0).getId());
        userService.setCalorieGoal(loggedInUser.getId(), 1000);
        LoggedInUser.updateInstance(userService.getUser(loggedInUser.getId()));
        Assertions.assertEquals(1000, foodFacade.caloriesLeftToday());
    }

    @Test
    void caloriesLeftTodayShouldReturnZeroIfCaloriesLeftIsNegative() throws JsonProcessingException {
        userService.setCalorieGoal(loggedInUser.getId(), 1);
        LoggedInUser.updateInstance(userService.getUser(loggedInUser.getId()));
        Assertions.assertEquals(0, foodFacade.caloriesLeftToday());
    }

    @Test
    void caloriesEatenInPercentageShouldReturnPercentageOfCaloriesEaten() throws JsonProcessingException {
        userService.setCalorieGoal(loggedInUser.getId(), 1000);
        LoggedInUser.updateInstance(userService.getUser(loggedInUser.getId()));
        Assertions.assertTrue(foodFacade.caloriesEatenInPercentage() > 0);
    }
}
