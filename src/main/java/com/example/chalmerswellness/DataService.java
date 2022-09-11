package com.example.chalmerswellness;

import java.io.File;
import java.sql.*;

public class DataService {
    private String dbPath = "C:\\sqlite\\db";

    public DataService(){
        createNewDatabase("testDb");
        createNewTable();
    }

    public void createNewDatabase(String fileName) {
        File sqliteFolder = new File(dbPath);
        if(!sqliteFolder.exists())
            sqliteFolder.mkdirs();

        dbPath += "/" + fileName;
        String url = "jdbc:sqlite:" + dbPath;

        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        String url = "jdbc:sqlite:C://sqlite/db/testDb.db";

        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
