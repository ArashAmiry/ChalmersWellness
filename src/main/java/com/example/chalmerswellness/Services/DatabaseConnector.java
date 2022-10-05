package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.LoggedInUser;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnector {
    private static String dbPath = "src/main/resources/ChalmersWellness.db";

    public DatabaseConnector() {
        createUsersTable();
        createNutritionTable();
        createExerciseTable();
        createCompletedExerciseTable();
        createCompletedSetTable();
        createCreatedWorkoutTable();
        createWorkoutExerciseTable();
        createFriendTable();
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
        String sql = "INSERT INTO nutrition(mealName, calories, servingSize, fatTotal, fatSaturated, protein, sodium, cholesterol, carbohydrates, fiber, sugar, mealOfDay, userID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
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
            pstmt.setInt(13, LoggedInUser.getInstance().getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeNutrition(int foodId){
        String sql = "DELETE FROM nutrition WHERE id = ? AND userID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            pstmt.setInt(2, LoggedInUser.getInstance().getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Food> getTodaysNutrition(Meal meal){
        String sql = "SELECT * FROM nutrition WHERE mealOfDay = ? AND userID = ? AND dateOfInsert = CURRENT_DATE";
        List<Food> foods = new ArrayList<>();

        try (Connection conn = this.connect();
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

    private void createNutritionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS nutrition (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + " userID INTEGER,\n"
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
                + " mealOfDay text NOT NULL, \n"
                + " FOREIGN KEY ('userID') REFERENCES 'users' ('id') \n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createFriendTable() {
        String sql = "CREATE TABLE IF NOT EXISTS friend (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	follower_id INTEGER,\n"
                + "	following_id INTEGER,\n"
                + " FOREIGN KEY ('follower_id') REFERENCES 'users' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"

                + " FOREIGN KEY ('following_id') REFERENCES 'users' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCreatedWorkoutTable() {
        String sql = "CREATE TABLE IF NOT EXISTS created_workout (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	workoutName TEXT\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createWorkoutExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS workout_exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	workout_id INTEGER,\n"
                + "	exercise_id INTEGER,\n"
                + "	sets_count INTEGER,\n"
                + " FOREIGN KEY ('workout_id') REFERENCES 'created_workout' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
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


    public static void createCompletedExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS completed_exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + " exercise_id INTEGER,\n"
                + " insert_date DATE DEFAULT CURRENT_DATE,\n"
                + " is_done bool,\n"
                + " planned_sets INTEGER,\n"
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

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " username text,\n"
                + " password text,\n"
                + " firstName text,\n"
                + " lastName text,\n"
                + " gender text,\n"
                + " email text,\n"
                + " birthDate date,\n"
                + " height integer,\n"
                + " weight double,\n"
                + " calorieGoal integer,\n"
                + " weightGoal double\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
