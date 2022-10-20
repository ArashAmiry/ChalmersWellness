package com.example.chalmerswellness;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CodeLogger {
    private static final Logger LOGGER = Logger.getLogger(CodeLogger.class.getName());


    public static void log(Level level, String msg, Throwable ex){
        LOGGER.log(level, msg, ex);
    }
    public static void log(Level level, String msg){
        LOGGER.log(level, msg);
    }




}
