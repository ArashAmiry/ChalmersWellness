package com.example.chalmerswellness.Services.testImplement;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.Services.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseRepository implements IDatabaseWorkoutRepository {

    public List<Exercise> getExercises() {
        String sql = "SELECT * FROM exercise";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
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

    public List<ExerciseItem> getCompletedExercises() {
        String sql = "SELECT * FROM completed_exercise WHERE insert_date = CURRENT_DATE";
        List<ExerciseItem> exerciseItems = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeQuery();

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                var id = rs.getInt("id");
                var exerciseId = rs.getInt("exercise_id");
                var isDone = rs.getBoolean("is_done");
                var planned_Sets = rs.getInt("planned_sets");

                ExerciseItem exerciseItem = new ExerciseItem(id, getExercise(exerciseId), getCompletedSets(id));
                exerciseItem.setDone(isDone);
                exerciseItem.setPlannedSetsCount(planned_Sets);
                exerciseItems.add(exerciseItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Collections.reverse(exerciseItems);
        return exerciseItems;
    }

    public List<Workout> getWorkouts(){
        String sql = "SELECT * FROM created_workout";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String workoutName = rs.getString("workoutName");
                List<ExerciseItem> exercises = getWorkoutExercises(id);
                Workout workout = new Workout(workoutName, exercises);
                workouts.add(workout);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return workouts;
    }

    public ExerciseItem insertCompletedExercise(ExerciseItem exercise){
        String sql = "INSERT INTO completed_exercise(exercise_id, is_done, planned_sets) VALUES(?,?,?)";
        int generatedKey = 0;

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exercise.getId());
                pstmt.setBoolean(2, exercise.getIsDone());
                pstmt.setInt(3, exercise.getSetsCount());
                pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new ExerciseItem(generatedKey, exercise);
    }

    public void insertCompletedExercises(List<ExerciseItem> exercises){
        for (ExerciseItem exerciseItem: exercises) {
            insertCompletedExercise(exerciseItem);
        }
    }

    public void insertWorkout(Workout workout) {
        String sql = "INSERT INTO created_workout(workoutName) VALUES(?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workout.getWorkoutName());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            insertWorkoutExercises(workout.getExercises(), generatedKey);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCompletedExercise(ExerciseItem exerciseItem){
        String sql = "UPDATE completed_exercise SET is_done = ? WHERE id = ?";
        int exerciseItemId = exerciseItem.getExerciseItemId();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, exerciseItem.getIsDone());
                pstmt.setInt(2, exerciseItemId);
                pstmt.executeUpdate();

                updateCompletedSets(exerciseItemId, exerciseItem.getSets());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void deleteCompletedExercise(ExerciseItem exercise) {
        String sql = "DELETE FROM completed_exercise WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exercise.getExerciseItemId());
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<ExerciseItemSet> getCompletedSets(int exerciseItemId) {
        String sql = "SELECT * FROM completed_set WHERE completed_exercise_id = ?";
        List<ExerciseItemSet> sets = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
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





    private static Exercise getExercise(int exercise_id) throws SQLException {
        String sql = "SELECT * FROM exercise WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exercise_id);

            ResultSet rs = pstmt.executeQuery();
            Exercise exercise = null;
            while (rs.next()) {
                var exerciseName = rs.getString("exerciseName");
                var type = rs.getString("exerciseType");
                var muscle = rs.getString("exerciseMuscle");
                var equipment = rs.getString("exerciseEquipment");
                var difficulty = rs.getString("exerciseDifficulty");
                var instructions = rs.getString("exerciseInstructions");

                exercise = new Exercise(exercise_id, exerciseName, type, muscle, equipment, difficulty, instructions);
            }
            return exercise;
        }
    }

    private static void removeCompletedSets(int exerciseId){
        String sql = "DELETE FROM completed_set WHERE completed_exercise_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exerciseId);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateCompletedSets(int exerciseId, List<ExerciseItemSet> sets){
        String sql = "INSERT INTO completed_set VALUES(?,?,?,?)";
        removeCompletedSets(exerciseId);

        try (Connection conn = DatabaseConnector.connect();
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

    public void insertExercises(List<Exercise> exercises) {
        String sql = "INSERT INTO exercise(exerciseName, exerciseType, exerciseMuscle, exerciseEquipment, exerciseDifficulty, exerciseInstructions) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Exercise exercise : exercises) {
                pstmt.setString(1, exercise.getName());
                pstmt.setString(2, exercise.getType());
                pstmt.setString(3, exercise.getMuscle());
                pstmt.setString(4, exercise.getEquipment());
                pstmt.setString(5, exercise.getDifficulty());
                pstmt.setString(6, exercise.getInstructions());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<ExerciseItem> getWorkoutExercises(int workoutId) throws SQLException {
        String sql = "SELECT * FROM workout_exercise WHERE workout_id = ?";

        List<ExerciseItem> exercises = new ArrayList<>();
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, workoutId);

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    var exerciseId  = rs.getInt("exercise_id");
                    int setCount = rs.getInt("sets_count");

                    Exercise exercise = getExercise(exerciseId);
                    ExerciseItem exerciseItem = new ExerciseItem(exercise);
                    exerciseItem.setPlannedSetsCount(setCount);
                    exercises.add(exerciseItem);
                }
            }

        return exercises;
    }

    private void insertWorkoutExercises(List<ExerciseItem> exercises, int id) {
        String sql = "INSERT INTO workout_exercise(exercise_id, workout_id, sets_count) VALUES(?,?,?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (ExerciseItem exercise : exercises) {
                pstmt.setInt(1, exercise.getId());
                pstmt.setInt(2, id);
                pstmt.setInt(3, exercise.getSetsCount());
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTodaysCompletedExercises() {
        String sql = "DELETE FROM completed_exercise WHERE insert_date = CURRENT_DATE";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*public void removeWorkout(int workoutId) {
        String sql = "DELETE FROM created_workout WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workoutId);
            pstmt.executeUpdate();

            removeExercises(workoutId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/


    /*public Exercise insertWorkoutExercise(Exercise exercise){
        String sql = "INSERT INTO workout_exercise VALUES(?,?,?)";
            String date = LocalDate.now().toString();
            int generatedKey = 0;

            try (Connection conn = DatabaseConnector.connect();
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
     */
}
