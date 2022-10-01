package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.sql.*;
import java.util.*;

public class DatabaseConnector {
    private static String dbPath = "src/main/resources/ChalmersWellness.db";

    public DatabaseConnector(){
        //createExercisesTable();
        createExerciseTable();
        createCompletedExerciseTable();
        createCompletedSetTable();
    }

    static Connection connect() {
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertNutrition(Food nutritionModel, Meal meal) {
        String sql = "INSERT INTO nutrition(mealName, calories, servingSize, fatTotal, fatSaturated, protein, sodium, cholesterol, carbohydrates, fiber, sugar, mealOfDay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nutritionModel.getName().substring(0, 1).toUpperCase() + nutritionModel.getName().substring(1));
            pstmt.setDouble(2, nutritionModel.getCalories());
            pstmt.setDouble(3, nutritionModel.getServingSize());
            pstmt.setDouble(4, nutritionModel.getFatTotal());
            pstmt.setDouble(5, nutritionModel.getFatSaturated());
            pstmt.setDouble(6, nutritionModel.getProtein());
            pstmt.setDouble(7, nutritionModel.getSodium());
            pstmt.setDouble(8, nutritionModel.getCholesterol());
            pstmt.setDouble(9, nutritionModel.getCarbohydrates());
            pstmt.setDouble(10, nutritionModel.getFiber());
            pstmt.setDouble(11, nutritionModel.getSugar());
            pstmt.setString(12, String.valueOf(meal));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeNutrition(int foodId){
        String sql = "DELETE FROM nutrition WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Food> getTodaysNutrition(Meal meal){
        String sql = "SELECT * FROM nutrition WHERE mealOfDay = ? AND dateOfInsert = CURRENT_DATE";
        List<Food> foods = new ArrayList<>();

        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
            pstmt.setString(1, String.valueOf(meal));

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

    private void createNutritionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS nutrition (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	mealName text NOT NULL,\n"
                + "	calories DOUBLE NOT NULL,\n"
                + "	servingSize DOUBLE NOT NULL,\n"
                + "	fatTotal DOUBLE NOT NULL,\n"
                + "	fatSaturated DOUBLE NOT NULL,\n"
                + "	protein DOUBLE NOT NULL,\n"
                + "	sodium DOUBLE NOT NULL,\n"
                + "	cholesterol DOUBLE NOT NULL,\n"
                + "	carbohydrates DOUBLE NOT NULL,\n"
                + "	fiber DOUBLE NOT NULL,\n"
                + "	sugar DOUBLE NOT NULL,\n"
                + " dateOfInsert DATE DEFAULT CURRENT_DATE,\n"
                + " mealOfDay text NOT NULL \n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCompletedSetTable() {
        String sql = "CREATE TABLE IF NOT EXISTS completed_set (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	completed_exercise_id INTEGER,\n"
                + "	weight DOUBLE NOT NULL,\n"
                + "	reps INTEGER NOT NULL,\n"
                + " FOREIGN KEY ('completed_exercise_id') REFERENCES 'completed_exercise' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

/*    private static void createExercisesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exercises (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    /*private void createExerciseItemTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exerciseItems (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	exerciseId INTEGER,\n"
                + " date TEXT\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/

    public static void createCompletedExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS completed_exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + " exercise_id INTEGER,\n"
                + " date TEXT,\n"
                + " FOREIGN KEY ('exercise_id') REFERENCES 'exercise' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	exerciseName text NOT NULL,\n"
                + "	exerciseType text NOT NULL,\n"
                + "	exerciseMuscle text NOT NULL,\n"
                + "	exerciseEquipment text NOT NULL,\n"
                + "	exerciseDifficulty text NOT NULL,\n"
                + "	exerciseInstructions text NOT NULL\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
