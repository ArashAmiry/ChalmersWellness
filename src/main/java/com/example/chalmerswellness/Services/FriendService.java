package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendService {
    public List<User> findFriends(String friendName){
        String sql = "SELECT * FROM users WHERE username LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + friendName + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getInt("height"),rs.getDate("birthDate").toLocalDate(), rs.getDouble("weight"), rs.getInt("calorieGoal"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }
}
