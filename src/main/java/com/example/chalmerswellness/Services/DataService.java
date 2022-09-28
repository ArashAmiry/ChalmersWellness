package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItemSet;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DataService {
    private final String dbPath = "src/main/resources/ChalmersWellness.db";

    public DataService() {
        createUsersTable();
        createNutritionTable();
    }

    public List<Workout> getWorkouts(){
        String sql = "SELECT * FROM workouts";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String workoutName = rs.getString("workoutName");
                List<Exercise> exercises = getWorkoutExercises(id);

                Workout workout = new Workout(workoutName, exercises);
                workouts.add(workout);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return workouts;
    }

    public List<Exercise> getWorkoutExercises(int workoutId) throws SQLException {
        String sql = "SELECT id, exerciseName, exerciseType, exerciseMuscle, exerciseEquipment, exerciseDifficulty, exerciseInstructions, workoutId FROM exercises WHERE workoutId = ?";

        List<Exercise> exercises = new ArrayList<>();
        try (Connection conn = this.connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workoutId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                var id = rs.getInt("id");
                var exerciseName = rs.getString("exerciseName");
                var type = rs.getString("exerciseType");
                var muscle = rs.getString("exerciseMuscle");
                var equipment = rs.getString("exerciseEquipment");
                var difficulty = rs.getString("exerciseDifficulty");
                var instructions = rs.getString("exerciseInstructions");

                Exercise exercise = new Exercise(id, exerciseName, type, muscle, equipment, difficulty, instructions);
                exercises.add(exercise);
            }
        }

        return exercises;
    }


    public void insertWorkout(Workout workout) {
        String sql = "INSERT INTO workouts(workoutName) VALUES(?)";

        try (Connection conn = connect(dbPath);
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

    private void insertWorkoutExercises(List<Exercise> exercises, int id) {
        String sql = "INSERT INTO exercises(exerciseName, exerciseType, exerciseMuscle, exerciseEquipment, exerciseDifficulty, exerciseInstructions, workoutId) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Exercise exercise : exercises) {
                pstmt.setString(1, exercise.getName());
                pstmt.setString(2, exercise.getType());
                pstmt.setString(3, exercise.getMuscle());
                pstmt.setString(4, exercise.getEquipment());
                pstmt.setString(5, exercise.getDifficulty());
                pstmt.setString(6, exercise.getInstructions());
                pstmt.setInt(7, id);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeWorkout(int workoutId) {
        String sql = "DELETE FROM workouts WHERE id = ?";

        try (Connection conn = this.connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workoutId);
            pstmt.executeUpdate();

            removeExercises(workoutId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removeExercises(int workoutId) {
        String sql = "DELETE FROM exercises WHERE workoutId = ?";

        try (Connection conn = this.connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, workoutId);
                pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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

    public void insertMyExercises(List<Exercise> exercises) {
        String sql = "INSERT INTO MyExercises(exerciseName, exerciseType, exerciseMuscle, exerciseEquipment, exerciseDifficulty, exerciseInstructions) VALUES(?,?,?,?,?,?)";

        try (Connection conn = connect(dbPath);
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

    public List<Exercise> getMyExercises() {
        String sql = "SELECT * FROM MyExercises";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
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

    private Exercise getMyExercise(int id) throws SQLException {
        String sql = "SELECT * FROM MyExercises WHERE id = ?";

        try (Connection conn = this.connect(dbPath);
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

    public List<Exercise> getTodayExerciseItems() {
        String todayDate = LocalDate.now().toString();

        String sql = "SELECT id, exerciseId FROM exerciseItems WHERE date = ?";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
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

    public Exercise insertExerciseItem(Exercise exercise) {
        String sql = "INSERT INTO exerciseItems VALUES(?,?,?)";
        String date = LocalDate.now().toString();
        int generatedKey = 0;

        try (Connection conn = connect(dbPath);
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

    public void removeExerciseItem(Exercise exercise) {
        String sql = "DELETE FROM exerciseItems WHERE id = ?";
        removeSets(exercise.getId());

        try (Connection conn = connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, exercise.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void insertExerciseSet(ExerciseItemSet set) {
        String sql = "INSERT INTO ExerciseSets VALUES(?,?,?,?)";

        try (Connection conn = connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(2, set.getId());
                pstmt.setDouble(3, set.getWeight());
                pstmt.setDouble(4, set.getReps());
                pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertSets(int exerciseId, List<ExerciseItemSet> sets){
        String sql = "INSERT INTO ExerciseSets VALUES(?,?,?,?)";
        removeSets(exerciseId);

        try (Connection conn = connect(dbPath);
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

    public void removeSets(int exerciseId){
        String sql = "DELETE FROM ExerciseSets WHERE exerciseItemId = ?";

        try (Connection conn = this.connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, exerciseId);
                pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeSet(int setId) {
        String sql = "DELETE FROM ExerciseSets WHERE id = ?";

        try (Connection conn = this.connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, setId);
                pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<ExerciseItemSet> getExerciseSets(int exerciseItemId) {
        String sql = "SELECT id,exerciseItemId, weight, reps FROM ExerciseSets WHERE exerciseItemId = ?";
        List<ExerciseItemSet> sets = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
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
    public void insertNutrition(Food nutritionModel, Meal meal) {
        String sql = "INSERT INTO nutrition(mealName, calories, servingSize, fatTotal, fatSaturated, protein, sodium, cholesterol, carbohydrates, fiber, sugar, mealOfDay) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect(dbPath);
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

        try (Connection conn = this.connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, foodId);
            pstmt.executeUpdate();

            removeExercises(foodId);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Food> getTodaysNutrition(Meal meal){
        String sql = "SELECT * FROM nutrition WHERE mealOfDay = ? AND dateOfInsert = CURRENT_DATE";
        List<Food> foods = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
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
                + "	id integer PRIMARY KEY,\n"
                + "	userID integer,\n"
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

    private void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INTEGER PRIMARY KEY,\n"
                + " username text,\n"
                + " password text \n"
                + ");";
        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Created users table");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(String username, String password) {
        String sql = "INSERT INTO users (username, password) VALUES(?,?)";
        try (Connection conn = connect(dbPath);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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



    // check if any users exist in database
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
