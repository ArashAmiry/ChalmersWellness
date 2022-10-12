package com.example.chalmerswellness.Services.NutritionServices;

import com.example.chalmerswellness.LoggedInUser;
import com.example.chalmerswellness.Services.DatabaseConnector;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseNutritionRepository implements IDatabaseNutritionRepository {

    @Override
    public void insertNutrition(Food nutritionModel, Meal meal) {
        String sql = "INSERT INTO nutrition(userID, mealName, calories, servingSize, fatTotal, fatSaturated, protein, sodium, cholesterol, carbohydrates, fiber, sugar, mealOfDay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, LoggedInUser.getInstance().getId());
            pstmt.setString(2, nutritionModel.getName().substring(0, 1).toUpperCase() + nutritionModel.getName().substring(1));
            pstmt.setDouble(3, nutritionModel.getCalories());
            pstmt.setDouble(4, nutritionModel.getServingSize());
            pstmt.setDouble(5, nutritionModel.getFatTotal());
            pstmt.setDouble(6, nutritionModel.getFatSaturated());
            pstmt.setDouble(7, nutritionModel.getProtein());
            pstmt.setDouble(8, nutritionModel.getSodium());
            pstmt.setDouble(9, nutritionModel.getCholesterol());
            pstmt.setDouble(10, nutritionModel.getCarbohydrates());
            pstmt.setDouble(11, nutritionModel.getFiber());
            pstmt.setDouble(12, nutritionModel.getSugar());
            pstmt.setString(13, String.valueOf(meal));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeNutrition(int foodId) {
        String sql = "DELETE FROM nutrition WHERE id = ? AND userID = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            pstmt.setInt(2, LoggedInUser.getInstance().getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Food> getTodaysNutrition(Meal meal) {
        String sql = "SELECT * FROM nutrition WHERE mealOfDay = ? AND userID = ? AND dateOfInsert = CURRENT_DATE";
        List<Food> foods = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, String.valueOf(meal));
            pstmt.setInt(2, LoggedInUser.getInstance().getId());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String mealName = rs.getString("mealName");
                double calories = rs.getInt("calories");

                Food food = new Food(id, mealName, calories);
                foods.add(food);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return foods;
    }
}
