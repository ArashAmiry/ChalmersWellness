package com.example.chalmerswellness.Services;

import com.example.chalmerswellness.CodeLogger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class DbConnectionService {
    private static String dbPath;
    private static DbConnectionService dbConnectionService = null;

    private DbConnectionService(boolean useActualDb)
    {
        dbPath = FileHandler.getDbUrl(useActualDb);
    }

    public static void createInstance(boolean useActualDb){
        if(dbConnectionService == null){
            dbConnectionService = new DbConnectionService(useActualDb);
        } else {
            CodeLogger.log(Level.WARNING, "Already created instance");
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
            CodeLogger.log(Level.WARNING, "Could not connect to database", e);
        }
        return conn;
    }
}
