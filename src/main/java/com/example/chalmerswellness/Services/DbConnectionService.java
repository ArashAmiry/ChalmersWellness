package com.example.chalmerswellness.Services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnectionService {
    private static String dbPath;
    private static DbConnectionService single_instance = null;

    private DbConnectionService(boolean useActualDb)
    {
        dbPath = getDbUrl(useActualDb);
    }

    private String getDbUrl(boolean useActualDb) {
        Properties p = new Properties();
        try{
            InputStream is = new FileInputStream("src/main/resources/dbConfig.txt");
            p.load(is);

            var url = p.getProperty("dbUrl");
            var testUrl = p.getProperty("testDbUrl");

            if(useActualDb){
                return url;
            } else {
                return testUrl;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
