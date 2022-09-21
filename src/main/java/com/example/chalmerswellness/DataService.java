package com.example.chalmerswellness;


import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.ExerciseItem;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private String dbPath = "C:\\sqlite\\db\\";
    public DataService(){
        createNewDatabase("testDb");
        createWorkoutTable();
        createExercisesTable();
        createExerciseItemTable();


        //ExercisesApiConnector connector = new ExercisesApiConnector();
        //var testExercises = connector.getExercises(1);
        createMyExercisesTable();
        //List<Exercise> test = new ArrayList<>();
        //insertExercises(testExercises);
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


    //TODO add connection to a exercise
    private void createExerciseItemTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exerciseItems (\n"
                + "	id INTEGER PRIMARY KEY,\n"
               // + "	exerciseId INTEGER,\n"
                + " date TEXT\n"
                + ");";

        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createMyExercisesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS MyExercises (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	exerciseName text NOT NULL,\n"
                + "	exerciseType text NOT NULL,\n"
                + "	exerciseMuscle text NOT NULL,\n"
                + "	exerciseEquipment text NOT NULL,\n"
                + "	exerciseDifficulty text NOT NULL,\n"
                + "	exerciseInstructions text NOT NULL\n"
                + ");";

        try (Connection conn = connect(dbPath);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //TODO REMOVE LATER, Temporary solution to progress
    public void insertExercises(List<Exercise> exercises) {
        String sql = "INSERT INTO MyExercises(exerciseName, exerciseType, exerciseMuscle, exerciseEquipment, exerciseDifficulty, exerciseInstructions) VALUES(?,?,?,?,?,?)";

        /*
        List<Exercise> testList = new ArrayList<>();
        Exercise test = new Exercise("Namn", "Typ", "annat", "Typ", "annat", "Typ");
        testList.add(test);

         */


        try (Connection conn = connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Exercise exercise : exercises) {
                pstmt.setString(1, exercise.name);
                pstmt.setString(2, exercise.type);
                pstmt.setString(3, exercise.muscle);
                pstmt.setString(4, exercise.equipment);
                pstmt.setString(5, exercise.difficulty);
                pstmt.setString(6, exercise.instructions);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Exercise> getMyExercises(){
        String sql = "SELECT * FROM MyExercises";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection conn = this.connect(dbPath);
             Statement stmt  = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("exerciseName");
                String type = rs.getString("exerciseType");
                String muscle = rs.getString("exerciseMuscle");
                String equipment = rs.getString("exerciseEquipment");
                String difficulty = rs.getString("exerciseDifficulty");
                String instructions = rs.getString("exerciseInstructions");



               Exercise exercise = new Exercise(name, type, muscle, equipment, difficulty, instructions);
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exercises;
    }

    //TODO give id to exercise
    public ExerciseItem insertExerciseItem(Exercise exercise) {
        String sql = "INSERT INTO exerciseItems VALUES(?,?)";

        String date = LocalDate.now().toString();

        try (Connection conn = connect(dbPath);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(2, date);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            return new ExerciseItem(generatedKey,exercise);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
