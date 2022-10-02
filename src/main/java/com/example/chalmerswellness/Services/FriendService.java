package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.User;

import java.sql.*;
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

    public void insertFollow(int followerID, int followingID){
        //TODO Make each row in table unique?
        String sql = "INSERT INTO friend (follower_id, following_id) VALUES(?,?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, followingID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean alreadyFollowing(int followerID, int followingID){
        String sql = "SELECT * FROM friend WHERE follower_id = ? AND following_id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, followerID);
            preparedStatement.setInt(2, followingID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
