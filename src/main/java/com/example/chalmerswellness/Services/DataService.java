package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.LoggedInUser;
import com.example.chalmerswellness.User;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private final String dbPath = "src/main/resources/ChalmersWellness.db";

    public DataService() {
    }

    private static Connection connect(String dbPath) {
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertNutrition(Food nutritionModel, Meal meal) {
        String sql = "INSERT INTO nutrition(userID, mealName, calories, servingSize, fatTotal, fatSaturated, protein, sodium, cholesterol, carbohydrates, fiber, sugar, mealOfDay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            System.out.println("insertNutrition: " + LoggedInUser.getInstance().getId());
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

    public void removeNutrition(int foodId){
        String sql = "DELETE FROM nutrition WHERE id = ? AND userID = ?";

        try (Connection conn = this.connect(dbPath);
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

        try (Connection conn = this.connect(dbPath);
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
                + "	id integer PRIMARY KEY,\n"
                + "	userID INTEGER,\n"
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

        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(User user, String password) {
        String sql = "INSERT INTO users (username, password, firstName, lastName, gender, email, birthDate, height, weight) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, String.valueOf(user.getGender()));
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setDate(7, Date.valueOf(user.getBirthDate()));
            preparedStatement.setInt(7, user.getHeight());
            preparedStatement.setDouble(8, user.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Return user object if user exists in database
    public User getUser(String username, String password) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            User user = null;
            try (Connection conn = connect(dbPath);
                 PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getInt("height"),rs.getDate("birthDate").toLocalDate(), rs.getDouble("weight"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return user;
        }

    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // Get userid from username
    public int getUserId(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean checkIfUsernameExists(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void setCalorieGoal(int userId, double calorieGoal) {
        String sql = "UPDATE users SET calorieGoal = ? WHERE id = ?";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDouble(1, calorieGoal);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkIfUsersExist() {
        String sql = "SELECT * FROM users";
        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
