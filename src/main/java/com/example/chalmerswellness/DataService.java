package com.example.chalmerswellness;


import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;
import com.example.chalmerswellness.calorieAPI.Food;
import com.example.chalmerswellness.calorieAPI.Meal;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataService {
    private String dbPath = "C:\\sqlite\\db\\";
    public DataService(){
        createNewDatabase("testDb");
        createWorkoutTable();
        createExercisesTable();
        createNutritionTable();
    }

    public List<Workout> getWorkouts(){
        String sql = "SELECT * FROM workouts";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

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
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
                pstmt.setInt(1,workoutId);

                ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                var exerciseName = rs.getString("exerciseName");
                var type = rs.getString("exerciseType");
                var muscle = rs.getString("exerciseMuscle");
                var equipment = rs.getString("exerciseEquipment");
                var difficulty = rs.getString("exerciseDifficulty");
                var instructions = rs.getString("exerciseInstructions");

                Exercise exercise = new Exercise(exerciseName, type, muscle, equipment, difficulty, instructions);
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
                pstmt.setString(1, exercise.name);
                pstmt.setString(2, exercise.type);
                pstmt.setString(3, exercise.muscle);
                pstmt.setString(4, exercise.equipment);
                pstmt.setString(5, exercise.difficulty);
                pstmt.setString(6, exercise.instructions);
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

    /*
    private void updateWorkout(int workoutId, Workout workout){
        //TODO Add Functionality
        String sql = "UPDATE workouts SET workoutName WHERE workoutId = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, workoutId);
            pstmt.setInt(2, work);
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
     */

    private static Connection connect(String dbPath) {
        String url = "jdbc:sqlite:"+dbPath+".db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createNewDatabase(String fileName) {
        File sqliteFolder = new File(dbPath);
        dbPath += fileName;

        if(!sqliteFolder.exists())
            sqliteFolder.mkdirs();

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = connect(dbPath);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createWorkoutTable() {
        String sql = "CREATE TABLE IF NOT EXISTS workouts (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	workoutName text NOT NULL UNIQUE\n"
                + ");";

        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createExercisesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exercises (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	exerciseName text NOT NULL,\n"
                + "	exerciseType text NOT NULL,\n"
                + "	exerciseMuscle text NOT NULL,\n"
                + "	exerciseEquipment text NOT NULL,\n"
                + "	exerciseDifficulty text NOT NULL,\n"
                + "	exerciseInstructions text NOT NULL,\n"
                + "	workoutId INTEGER NOT NULL\n"
                + ");";

        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createNutritionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS nutrition (\n"
                + "	id INTEGER PRIMARY KEY,\n"
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
                + " mealOfDay text NOT NULL \n"
                + ");";

        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
