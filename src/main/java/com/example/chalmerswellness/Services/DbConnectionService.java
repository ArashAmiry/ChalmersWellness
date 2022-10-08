package com.example.chalmerswellness.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionService {

    private static String dbPath;
    private static DbConnectionService single_instance = null;

    public String getDbPath(){
        return dbPath;
    }

    private DbConnectionService(String dbPath)
    {
        this.dbPath = dbPath;
    }

    public static void createInstance(String dbPath){
        if(single_instance == null){
            single_instance = new DbConnectionService(dbPath);
        } else {
            System.out.println("Already Created Instance!");
        }
    }

    public static DbConnectionService getInstance()
    {
        if (single_instance != null)
            return single_instance;
        return null;
    }

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
