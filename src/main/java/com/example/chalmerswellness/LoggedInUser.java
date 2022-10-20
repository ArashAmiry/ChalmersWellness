package com.example.chalmerswellness;

public class LoggedInUser {

    private static User instance;

    /**
     * Creates new instance of User if not null
     * @param user the user which logs in
     */
    public static void createInstance(User user){
        if (instance == null) {
            instance = user;
        }
    }

    /**
     *
     * @return User instance
     */
    public static User getInstance() {
        return instance;
    }

    /**
     * Sets User instance to null
     */
    public static void destroyInstance(){
        instance = null;
    }

    /**
     * Sets User instance to null and creates new User instance
     * @param user user which is to be updated
     */
    public static void updateInstance(User user){
        destroyInstance();
        createInstance(user);
    }

}
