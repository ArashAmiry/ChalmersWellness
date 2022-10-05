package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.Gender;
import com.example.chalmerswellness.User;

import java.sql.*;
import java.time.LocalDate;

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

    public void insertUser(User user) {
        String sql = "INSERT INTO users (username, password, firstName, lastName, gender, email, birthDate, height, weight) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, String.valueOf(user.getGender()));
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.setDate(7, Date.valueOf(user.getBirthDate()));
            preparedStatement.setInt(8, user.getHeight());
            preparedStatement.setDouble(9, user.getWeight());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUser(int id, String username, String password, String firstName, String lastName, Gender gender, String email,  LocalDate birthDate, int height, double weight) {
        String sql = "UPDATE users SET "
                + "username = ?,"
                + "password = ?,"
                + "firstName = ?,"
                + "lastName = ?,"
                + "gender = ?,"
                + "email = ?,"
                + "birthDate = ?,"
                + "height = ?,"
                + "weight = ?"
                + "WHERE id = ?";
        try (Connection conn = connect(dbPath);
              PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, gender.toString());
            preparedStatement.setString(6, email);
            preparedStatement.setDate(7, Date.valueOf(birthDate));
            preparedStatement.setInt(8, height);
            preparedStatement.setDouble(9, weight);
            preparedStatement.setInt(10, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public User getUser(String username, String password) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            User user = null;
            try (Connection conn = connect(dbPath);
                 PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                user = getUserFromRow(user, preparedStatement);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return user;
        }

    public User getUser(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            user = getUserFromRow(user, preparedStatement);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    private User getUserFromRow(User user, PreparedStatement preparedStatement) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("firstName"), rs.getString("lastName"), Gender.valueOf(rs.getString("gender")), rs.getString("email"), rs.getInt("height"),rs.getDate("birthDate").toLocalDate(), rs.getDouble("weight"), rs.getInt("calorieGoal"));
        }
        return user;
    }

    public boolean checkIfCredentialsMatch(String username, String password) {
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


    public void setCalorieGoal(int id, double calorieGoal) {
        String sql = "UPDATE users SET calorieGoal = ? WHERE id = ?";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDouble(1, calorieGoal);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setWeightGoal(int id, double weightGoal) {
        String sql = "UPDATE users SET weightGoal = ? WHERE id = ?";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setDouble(1, weightGoal);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
