package com.example.chalmerswellness.Services.FriendServices;

import com.example.chalmerswellness.Enums.Gender;
import com.example.chalmerswellness.Models.LoggedInUser;
import com.example.chalmerswellness.Services.DbConnectionService;
import com.example.chalmerswellness.ObjectModels.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseFriendRepository implements IDatabaseFriendRepository{

    public List<User> findFriends(String friendName){
        String sql = "SELECT * FROM users WHERE username LIKE ?";
        List<User> users = new ArrayList<>();

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + friendName + "%");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("firstName"), rs.getString("lastName"), Gender.valueOf(rs.getString("gender")), rs.getString("email"), rs.getInt("height"),rs.getDate("birthDate").toLocalDate(), rs.getDouble("weight"), rs.getInt("calorieGoal"), rs.getDouble("weightGoal"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public void insertFollow(int followingID){
        //TODO Make each row in table unique?
        String sql = "INSERT INTO friend (follower_id, following_id) VALUES(?,?)";
        try (Connection conn = DbConnectionService.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, LoggedInUser.getInstance().getId());
            preparedStatement.setInt(2, followingID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeFollow(int following_id){
        String sql = "DELETE FROM friend WHERE follower_id = ? AND following_id = ?";

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, LoggedInUser.getInstance().getId());
            pstmt.setInt(2, following_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean alreadyFollowing(int followerID, int followingID){
        String sql = "SELECT * FROM friend WHERE follower_id = ? AND following_id = ?";
        try (Connection conn = DbConnectionService.connect();
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
