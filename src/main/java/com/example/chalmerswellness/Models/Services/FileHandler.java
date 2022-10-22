package com.example.chalmerswellness.Models.Services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class FileHandler {

    public static String getDbUrl(boolean useActualDb) {
        Properties properties = new Properties();
        try{
            InputStream is = Files.newInputStream(Paths.get("src/main/resources/dbConfig.txt"));
            properties.load(is);

            var dbUrl = properties.getProperty("dbUrl");
            var testDbUrl = properties.getProperty("testDbUrl");

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
