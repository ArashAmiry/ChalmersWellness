package com.example.chalmerswellness.Models.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DbConnectionService {
    private static String dbPath;
    private static DbConnectionService dbConnectionService;

    /**
     * This constructor fetches the url to the database.
     * <p>
     * @param useActualDb This decides if an actual database will be used or a test database.
     */
    private DbConnectionService(boolean useActualDb)
    {
        dbPath = FileHandler.getDbUrl(useActualDb);
    }

    /**
     * This method creates an instance of the DbConnectionService.
     * <p>
     * @param useActualDb This decides if an actual database will be used or a test database.
     */
    public static void createInstance(boolean useActualDb){
        if(dbConnectionService == null){
            dbConnectionService = new DbConnectionService(useActualDb);
        } else {
            System.out.println("Already Created Instance!");
        }
    }

    /**
     * This method fetches a created instance of the DbConnectionService
     * <p>
     * @return DbConnectionService the created DbConnectionService
     */
    public static DbConnectionService getInstance() {
        if (dbConnectionService != null) {
            return dbConnectionService;
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * This method connects to the database through dbPath.
     * <p>
     * @return Connection the connection to the database
     */
    public static Connection connect() {
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
}
