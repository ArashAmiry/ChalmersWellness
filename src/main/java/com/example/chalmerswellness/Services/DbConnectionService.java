package com.example.chalmerswellness.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionService {
    private static String dbPath;
    private static DbConnectionService single_instance = null;

    private DbConnectionService(boolean useActualDb)
    {
        dbPath = FileHandler.getDbUrl(useActualDb);
    }

    public static void createInstance(boolean useActualDb){
        if(single_instance == null){
            single_instance = new DbConnectionService(useActualDb);
        } else {
            System.out.println("Already Created Instance!");
        }
    }

    public static DbConnectionService getInstance()
    {
        if (single_instance != null) {
            return single_instance;
        } else {
            throw new RuntimeException("No created instance of " + DbConnectionService.class);
        }
    }

    public static Connection connect() {
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
