package com.example.chalmerswellness;

public class LoggedInUser {

    private static User instance = null;

    private LoggedInUser() {
    }

    public static void createInstance(User user){
        if(instance == null){
            instance = user;
        }
    }

    public static User getInstance() {
        return instance;
    }

    public static void destroyInstance(){
        instance = null;
    }

    public static void updateInstance(User user){
        destroyInstance();
        createInstance(user);
    }

}
