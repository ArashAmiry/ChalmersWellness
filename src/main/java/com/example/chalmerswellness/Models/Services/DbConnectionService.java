package com.example.chalmerswellness.Models.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionService {
    private static String dbPath;
    private static DbConnectionService dbConnectionService = null;

    private DbConnectionService(boolean useActualDb)
    {
        dbPath = FileHandler.getDbUrl(useActualDb);
    }
    public static void createInstance(boolean useActualDb){
        if(dbConnectionService == null){
            dbConnectionService = new DbConnectionService(useActualDb);
        }
}

    public static DbConnectionService getInstance()
    {
        if (dbConnectionService != null) {
            return dbConnectionService;
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
