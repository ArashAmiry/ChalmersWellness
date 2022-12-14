package com.example.chalmerswellness.Models.Services.WorkoutServices;

import com.example.chalmerswellness.Models.AccountModel.LoggedInUser;
import com.example.chalmerswellness.Models.Services.DbConnectionService;
import com.example.chalmerswellness.Models.ObjectModels.Exercise;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.Models.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.Models.ObjectModels.Workout;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseWorkoutRepository implements IDatabaseWorkoutRepository {

    /**
     * This method fetches all the exercises in the database.
     * @return all the exercises in the database.
     */
    public List<Exercise> getExercises() {
        String sql = "SELECT * FROM exercise";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = DbConnectionService.connect();
             Statement stmt = conn.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("exerciseName");
                String type = resultSet.getString("exerciseType");
                String muscle = resultSet.getString("exerciseMuscle");
                String equipment = resultSet.getString("exerciseEquipment");
                String difficulty = resultSet.getString("exerciseDifficulty");
                String instructions = resultSet.getString("exerciseInstructions");

                Exercise exercise = new Exercise(id, name, type, muscle, equipment, difficulty, instructions);
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return exercises;
    }

    /**
     * This method fetches all the completed exercises in the database.
     * @return a list of all the exerciseItems in the database.
     */
    public List<ExerciseItem> getCompletedExercises() {
        String sql = "SELECT * FROM completed_exercise WHERE insert_date = CURRENT_DATE AND user_id = ?";
        List<ExerciseItem> exerciseItems = new ArrayList<>();

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, LoggedInUser.getInstance().getId());
                pstmt.executeQuery();
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int exerciseId = resultSet.getInt("exercise_id");
                boolean isDone = resultSet.getBoolean("is_done");
                int plannedSets = resultSet.getInt("planned_sets");

                ExerciseItem exerciseItem = new ExerciseItem(id, getExercise(exerciseId), getCompletedSets(id));
                exerciseItem.setDone(isDone);
                exerciseItem.setPlannedSetsCount(plannedSets);
                exerciseItems.add(exerciseItem);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Collections.reverse(exerciseItems);
        return exerciseItems;
    }

    /**
     * This method fetches all the completed exercises in the database for a certain date and user
     * @param date is when the exercise was completed
     * @param userId id of the user
     * @return a list of all the exerciseItems in the database for a certain date.
     */
    public List<ExerciseItem> getCompletedExercises(LocalDate date, int userId) {
        String sql = "SELECT * FROM completed_exercise WHERE insert_date = ? AND user_id = ?";
        List<ExerciseItem> exerciseItems = new ArrayList<>();

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date.toString());
            pstmt.setInt(2, userId);
            pstmt.executeQuery();
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int exerciseId = resultSet.getInt("exercise_id");
                boolean isDone = resultSet.getBoolean("is_done");
                int plannedSets = resultSet.getInt("planned_sets");

                ExerciseItem exerciseItem = new ExerciseItem(id, getExercise(exerciseId), getCompletedSets(id));
                exerciseItem.setDone(isDone);
                exerciseItem.setPlannedSetsCount(plannedSets);
                exerciseItems.add(exerciseItem);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Collections.reverse(exerciseItems);
        return exerciseItems;
    }

    /**
     * This method fetches all the created workouts.
     * @return a list of all the created workouts in the database.
     */
    public List<Workout> getWorkouts(){
        String sql = "SELECT * FROM created_workout WHERE user_id = ?";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, LoggedInUser.getInstance().getId());
                pstmt.executeQuery();

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String workoutName = resultSet.getString("workoutName");
                List<ExerciseItem> exercises = getWorkoutExercises(id);
                Workout workout = new Workout(id, workoutName, exercises);
                workouts.add(workout);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return workouts;
    }

    /**
     * This method inserts a exerciseItem as a completed exercise.
     * @return ExerciseItem that was just inserted
     */
    public ExerciseItem insertCompletedExercise(ExerciseItem exercise){
        String sql = "INSERT INTO completed_exercise(exercise_id, user_id, is_done, planned_sets) VALUES(?,?,?,?)";
        int generatedKey = 0;

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exercise.getId());
                pstmt.setInt(2, LoggedInUser.getInstance().getId());
                pstmt.setBoolean(3, exercise.isDone());
                pstmt.setInt(4, exercise.getSetsCount());
                pstmt.executeUpdate();

            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return new ExerciseItem(generatedKey, exercise);
    }

    /**
     * This method inserts a list of exerciseItems that was completed.
     * @param exercises the exerciseItems that will be inserted into the database
     */
    public void insertCompletedExercises(List<ExerciseItem> exercises){
        for (ExerciseItem exerciseItem: exercises) {
            insertCompletedExercise(exerciseItem);
        }
    }

    /**
     * This method inserts a workout to the database.
     * @param workout is inserted to the database
     */
    public void insertWorkout(Workout workout) {
        String sql = "INSERT INTO created_workout(workoutName, user_id) VALUES(?,?)";

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, workout.getWorkoutName());
            pstmt.setInt(2, LoggedInUser.getInstance().getId());
            pstmt.executeUpdate();

            ResultSet resultSet = pstmt.getGeneratedKeys();
            int generatedKey = 0;
            if (resultSet.next()) {
                generatedKey = resultSet.getInt(1);
            }

            insertWorkoutExercises(workout.getExercises(), generatedKey);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method updates an exerciseItem
     * @param exerciseItem is the item that will be updated in the database.
     */
    public void updateCompletedExercise(ExerciseItem exerciseItem){
        String sql = "UPDATE completed_exercise SET is_done = ? WHERE id = ?";
        int exerciseItemId = exerciseItem.getExerciseItemId();

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setBoolean(1, exerciseItem.isDone());
                pstmt.setInt(2, exerciseItemId);
                pstmt.executeUpdate();

                updateCompletedSets(exerciseItemId, exerciseItem.getSets());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method deletes an exerciseItem from the database
     * @param exercise is the exerciseItem that will be removed.
     */
    public void deleteCompletedExercise(ExerciseItem exercise) {
        String sql = "DELETE FROM completed_exercise WHERE id = ?";

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exercise.getExerciseItemId());
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method fetches all the completed sets from a specific exerciseItem by id.
     * <p>
     * @param exerciseItemId is the id where the sets are associated with.
     * @return List<ExerciseItemSet>
     */
    private List<ExerciseItemSet> getCompletedSets(int exerciseItemId) {
        String sql = "SELECT * FROM completed_set WHERE completed_exercise_id = ?";
        List<ExerciseItemSet> sets = new ArrayList<>();

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exerciseItemId);
            pstmt.executeQuery();

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                double weight = resultSet.getDouble("weight");
                int reps = resultSet.getInt("reps");
                ExerciseItemSet set = new ExerciseItemSet(weight, reps);
                sets.add(set);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sets;
    }

    /**
     * This method fetches an exercise by id.
     * <p>
     * @param exerciseId is the id of the exercise.
     * @return Exercise
     */
    private static Exercise getExercise(int exerciseId) throws SQLException {
        String sql = "SELECT * FROM exercise WHERE id = ?";

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exerciseId);

            ResultSet resultSet = pstmt.executeQuery();
            Exercise exercise = null;
            while (resultSet.next()) {
                String exerciseName = resultSet.getString("exerciseName");
                String type = resultSet.getString("exerciseType");
                String muscle = resultSet.getString("exerciseMuscle");
                String equipment = resultSet.getString("exerciseEquipment");
                String difficulty = resultSet.getString("exerciseDifficulty");
                String instructions = resultSet.getString("exerciseInstructions");

                exercise = new Exercise(exerciseId, exerciseName, type, muscle, equipment, difficulty, instructions);
            }
            return exercise;
        }
    }

    /**
     * This method removes all the completed sets for a specific exercise by id.
     * <p>
     * @param exerciseId is the id of the exercise.
     */
    private static void removeCompletedSets(int exerciseId){
        String sql = "DELETE FROM completed_set WHERE completed_exercise_id = ?";

        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exerciseId);
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method updates an exercise with the list of sets.
     * <p>
     * @param exerciseId is the id of the exercise.
     * @param sets is the list of sets that will be used to update the exercise.
     */
    private void updateCompletedSets(int exerciseId, List<ExerciseItemSet> sets){
        String sql = "INSERT INTO completed_set VALUES(?,?,?,?)";
        removeCompletedSets(exerciseId);

        try (Connection conn = DbConnectionService.connect();
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

    /**
     * This method inserts a list of exercises to the database.
     * @param exercises is the list of all the exercises that will be added to the database.
     */
    public void insertExercises(List<Exercise> exercises) {
        String sql = "INSERT INTO exercise(exerciseName, exerciseType, exerciseMuscle, exerciseEquipment, exerciseDifficulty, exerciseInstructions) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DbConnectionService.connect();
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

    /**
     * This method fetches all the exerciseItems that is associated with that workout id.
     * <p>
     * @param workoutId is the id of the workout.
     * @return List<ExerciseItem> the exerciseItems on the workout.
     */
    private List<ExerciseItem> getWorkoutExercises(int workoutId) throws SQLException {
        String sql = "SELECT * FROM workout_exercise WHERE workout_id = ?";

        List<ExerciseItem> exercises = new ArrayList<>();
        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, workoutId);

                ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                    int exerciseId  = resultSet.getInt("exercise_id");
                    int setCount = resultSet.getInt("sets_count");

                    Exercise exercise = getExercise(exerciseId);
                    ExerciseItem exerciseItem = new ExerciseItem(exercise);
                    exerciseItem.setPlannedSetsCount(setCount);
                    exercises.add(exerciseItem);
                }
            }

        return exercises;
    }

    /**
     * This method inserts exerciseItems into a workout with a specific id.
     * <p>
     * @param exercises is the exerciseItems that will be added to the workout.
     * @param id of the workout.
     */
    private void insertWorkoutExercises(List<ExerciseItem> exercises, int id) {
        String sql = "INSERT INTO workout_exercise(exercise_id, workout_id, sets_count) VALUES(?,?,?)";
        try (Connection conn = DbConnectionService.connect();
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

    /**
     * This method removes a workout from the database.
     * @param workout is to be removed.
     */
    public void deleteSavedWorkout(Workout workout) {
        String sql = "DELETE FROM created_workout WHERE id = ?";
        try (Connection conn = DbConnectionService.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, workout.getId());
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
