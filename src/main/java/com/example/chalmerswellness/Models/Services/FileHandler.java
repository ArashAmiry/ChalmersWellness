package com.example.chalmerswellness.Models.Services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class FileHandler {

    /**
     * This method fetches the path to the database through a config file "dbConfig.txt".
     * <p>
     * @param useActualDb This decides if an actual database will be used or a test database.
     * @return String This returns an url to the database
     */
    public static String getDbUrl(boolean useActualDb) {
        Properties properties = new Properties();
        try{
            InputStream is = Files.newInputStream(Paths.get("src/main/resources/dbConfig.txt"));
            properties.load(is);

            String dbUrl = properties.getProperty("dbUrl");
            String testDbUrl = properties.getProperty("testDbUrl");

            if(useActualDb){
                return dbUrl;
            } else {
                return testDbUrl;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
