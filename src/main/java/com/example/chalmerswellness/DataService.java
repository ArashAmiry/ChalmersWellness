package com.example.chalmerswellness;


import com.example.chalmerswellness.ObjectModels.Exercise;
import com.example.chalmerswellness.ObjectModels.Workout;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private String dbPath = "C:\\sqlite\\db\\";
    //private Connection conn;
    public DataService(){
        createNewDatabase("testDb");
        createNewTable();


        List< Exercise > exerciseList = new ArrayList<>();
        exerciseList.add(new Exercise("DumbbellCurls", "FeelsStrongMan", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        exerciseList.add(new Exercise("asdasd", "asdashghhhhh", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        exerciseList.add(new Exercise("qwewqeqwe", "wrewer", "bicep", "dumbbells", "Extremly difficult", "bara kör"));
        Workout workout = new Workout("PushPull", exerciseList);
        insertWorkout(workout);



    }

    public void insertWorkout(Workout workout){
        insert(workout);
    }
    public void insert(Workout workout) {
        String sql = "INSERT INTO workouts(workout.getWorkoutName(), workout.getExercises()) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, workoutName);
                pstmt.setInt(2, exercises);
                pstmt.executeUpdate();
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
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createNewTable() {
        String url = "jdbc:sqlite:C://sqlite/db/testDb.db";

        String sql = "CREATE TABLE IF NOT EXISTS workouts (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	workoutName text NOT NULL,\n"
                + "	exercises integer\n"
                + ");";



        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }




}
