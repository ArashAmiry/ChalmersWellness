package com.example.chalmerswellness.Services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class FileHandler {

    public static String getDbUrl(boolean useActualDb) {
        Properties p = new Properties();
        try{
            InputStream is = Files.newInputStream(Paths.get("src/main/resources/dbConfig.txt"));
            p.load(is);

            var dbUrl = p.getProperty("dbUrl");
            var testDbUrl = p.getProperty("testDbUrl");

            if(useActualDb){
                return dbUrl;
            } else {
                return testDbUrl;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
