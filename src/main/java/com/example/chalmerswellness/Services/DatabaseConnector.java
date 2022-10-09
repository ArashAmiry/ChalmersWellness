package com.example.chalmerswellness.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private static String dbPath = "src/main/resources/ChalmersWellness.db";

    public DatabaseConnector() {
        createUsersTable();
        createNutritionTable();
        createExerciseTable();
        createCompletedExerciseTable();
        createCompletedSetTable();
        createCreatedWorkoutTable();
        createWorkoutExerciseTable();
        createFriendTable();
    }

    static Connection connect() {
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createNutritionTable() {
        String sql = "CREATE TABLE IF NOT EXISTS nutrition (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + " userID INTEGER,\n"
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

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createFriendTable() {
        String sql = "CREATE TABLE IF NOT EXISTS friend (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	follower_id INTEGER,\n"
                + "	following_id INTEGER,\n"
                + " FOREIGN KEY ('follower_id') REFERENCES 'users' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"

                + " FOREIGN KEY ('following_id') REFERENCES 'users' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCreatedWorkoutTable() {
        String sql = "CREATE TABLE IF NOT EXISTS created_workout (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	workoutName TEXT\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createWorkoutExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS workout_exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	workout_id INTEGER,\n"
                + "	exercise_id INTEGER,\n"
                + "	sets_count INTEGER,\n"
                + " FOREIGN KEY ('workout_id') REFERENCES 'created_workout' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + " FOREIGN KEY ('exercise_id') REFERENCES 'exercise' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createCompletedSetTable() {
        String sql = "CREATE TABLE IF NOT EXISTS completed_set (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	completed_exercise_id INTEGER,\n"
                + "	weight DOUBLE NOT NULL,\n"
                + "	reps INTEGER NOT NULL,\n"
                + " FOREIGN KEY ('completed_exercise_id') REFERENCES 'completed_exercise' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

/*    private static void createExercisesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exercises (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/


    public static void createCompletedExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS completed_exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + " exercise_id INTEGER,\n"
                + " insert_date DATE DEFAULT CURRENT_DATE,\n"
                + " is_done bool,\n"
                + " planned_sets INTEGER,\n"
                + " FOREIGN KEY ('exercise_id') REFERENCES 'exercise' ('id')\n"
                + " ON UPDATE CASCADE ON DELETE CASCADE\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createExerciseTable() {
        String sql = "CREATE TABLE IF NOT EXISTS exercise (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	exerciseName text NOT NULL,\n"
                + "	exerciseType text NOT NULL,\n"
                + "	exerciseMuscle text NOT NULL,\n"
                + "	exerciseEquipment text NOT NULL,\n"
                + "	exerciseDifficulty text NOT NULL,\n"
                + "	exerciseInstructions text NOT NULL\n"
                + ");";
        try (Connection conn = connect();
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
                + " password text,\n"
                + " firstName text,\n"
                + " lastName text,\n"
                + " gender text,\n"
                + " email text,\n"
                + " birthDate date,\n"
                + " height integer,\n"
                + " weight double,\n"
                + " calorieGoal integer,\n"
                + " weightGoal double\n"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
