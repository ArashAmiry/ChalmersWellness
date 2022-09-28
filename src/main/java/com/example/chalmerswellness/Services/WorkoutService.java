package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.chalmerswellness.Services.Database.connect;


public class WorkoutService {
    //private static Database db = Database.getInstance();

    public static Exercise insertExerciseItem(Exercise exercise){
        String sql = "INSERT INTO exerciseItems VALUES(?,?,?)";
            String date = LocalDate.now().toString();
            int generatedKey = 0;

            try (Connection conn = connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(2, exercise.getId());
                    pstmt.setString(3, date);
                    pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getInt(1);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        return new Exercise(generatedKey, exercise);
    }

    public static void insertExerciseSet(ExerciseItemSet set) {
        String sql = "INSERT INTO ExerciseSets VALUES(?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(2, set.getId());
            pstmt.setDouble(3, set.getWeight());
            pstmt.setDouble(4, set.getReps());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Exercise> getTodayExerciseItems() {
        String todayDate = LocalDate.now().toString();

        String sql = "SELECT id, exerciseId FROM exerciseItems WHERE date = ?";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, todayDate);
            pstmt.executeQuery();

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                var exerciseId = rs.getInt("exerciseId");
                Exercise exercise = getMyExercise(exerciseId);
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exercises;
    }

    public static void removeSet(int setId) {
        String sql = "DELETE FROM ExerciseSets WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, setId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<ExerciseItemSet> getExerciseSets(int exerciseItemId) {
        String sql = "SELECT id,exerciseItemId, weight, reps FROM ExerciseSets WHERE exerciseItemId = ?";
        List<ExerciseItemSet> sets = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exerciseItemId);
            pstmt.executeQuery();

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                var id = rs.getInt("id");
                double weight = rs.getDouble("weight");
                int reps = rs.getInt("reps");
                ExerciseItemSet set = new ExerciseItemSet(id, weight, reps);
                sets.add(set);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sets;
    }

    public static List<Exercise> getMyExercises() {
        String sql = "SELECT * FROM MyExercises";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("exerciseName");
                String type = rs.getString("exerciseType");
                String muscle = rs.getString("exerciseMuscle");
                String equipment = rs.getString("exerciseEquipment");
                String difficulty = rs.getString("exerciseDifficulty");
                String instructions = rs.getString("exerciseInstructions");

                Exercise exercise = new Exercise(id, name, type, muscle, equipment, difficulty, instructions);
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exercises;
    }

    private static Exercise getMyExercise(int id) throws SQLException {
        String sql = "SELECT * FROM MyExercises WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            Exercise exercise = null;
            while (rs.next()) {
                var exerciseName = rs.getString("exerciseName");
                var type = rs.getString("exerciseType");
                var muscle = rs.getString("exerciseMuscle");
                var equipment = rs.getString("exerciseEquipment");
                var difficulty = rs.getString("exerciseDifficulty");
                var instructions = rs.getString("exerciseInstructions");

                exercise = new Exercise(id, exerciseName, type, muscle, equipment, difficulty, instructions);
            }
            return exercise;
        }
    }

    public static void removeExerciseItem(Exercise exercise) {
        String sql = "DELETE FROM exerciseItems WHERE id = ?";
        removeSets(exercise.getId());

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exercise.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeSets(int exerciseId){
        String sql = "DELETE FROM ExerciseSets WHERE exerciseItemId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exerciseId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertSets(int exerciseId, List<ExerciseItemSet> sets){
        String sql = "INSERT INTO ExerciseSets VALUES(?,?,?,?)";
        removeSets(exerciseId);

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (ExerciseItemSet set : sets) {
                pstmt.setInt(2, exerciseId);
                pstmt.setDouble(3, set.getWeight());
                pstmt.setInt(4, set.getReps());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
