package com.example.chalmerswellness;


import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private String dbPath = "C:\\sqlite\\db\\";
    public DataService(){
        createNewDatabase("testDb");

        createWorkoutTable();
        createExercisesTable();

        List< Exercise > exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("DumbbellCurls", "FeelsStrongMan", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        exerciseList.add(new Exercise("BenchPress", "asdashghhhhh", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        exerciseList.add(new Exercise("Deadlift", "wrewer", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        Workout workout = new Workout("PushPull", exerciseList);
        insertWorkout(workout);

        List< Exercise > exerciseList2 = new ArrayList<>();
        exerciseList2.add(new Exercise("OtherExercise", "FeelsStrongMan", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        exerciseList2.add(new Exercise("Dumbbellcurl", "asdashghhhhh", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        exerciseList2.add(new Exercise("lifting weights", "wrewer", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        Workout workout2 = new Workout("Dumb Workout", exerciseList);
        insertWorkout(workout2);
    }


    public List<Workout> getWorkouts(){
        String sql = "SELECT * FROM workouts";
        List<Workout> workouts = new ArrayList<>();

        try (Connection conn = this.connect();
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
        String sql = "SELECT id, exerciseName, workoutId FROM exercises WHERE workoutId = ?";

        List<Exercise> exercises = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){
                pstmt.setInt(1,workoutId);

                ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
                var exerciseName = rs.getString("exerciseName");

                Exercise exercise = new Exercise(exerciseName, "", "", "", "", "");
                exercises.add(exercise);
            }

        }

        return exercises;
    }


    public void insertWorkout(Workout workout) {
        String sql = "INSERT INTO workouts(workoutName) VALUES(?)";

        try (Connection conn = this.connect();
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
        String sql = "INSERT INTO exercises(exerciseName, workoutId) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (var element : exercises) {
                pstmt.setString(1, element.name);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




    private Connection connect() {
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
        if(!sqliteFolder.exists())
            sqliteFolder.mkdirs();

        dbPath += fileName;
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = connect();
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
                + "	workoutName text NOT NULL UNIQUE,\n"
                + "	date Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP\n"
                + ");";

        try (Connection conn = connect();
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
                + "	workoutId INTEGER NOT NULL,\n"
                + "	date Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
