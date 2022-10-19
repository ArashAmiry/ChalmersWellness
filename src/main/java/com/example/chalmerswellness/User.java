package com.example.chalmerswellness;

import java.time.LocalDate;

public class User {
    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private int height;
    private LocalDate birthDate;
    private double weight;
    private int calorieGoal;
    private double weightGoal;

    /**
     * Instantiates user
     * @param username users username
     * @param password users password
     * @param firstName users first name
     * @param lastName users last name
     * @param gender users gender
     * @param email users email
     * @param birthDate users birthdate
     * @param height users height
     * @param weight users weight
     */
    public User(String username, String password, String firstName, String lastName, Gender gender, String email, LocalDate birthDate, int height, double weight) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
    }

    /**
     * Instantiates user
     * @param id users id
     * @param username users username
     * @param password users password
     * @param firstName users first name
     * @param lastName users last name
     * @param gender users gender
     * @param email users email
     * @param height users height
     * @param birthDate users birthdate
     * @param weight users weight
     * @param calorieGoal users calorie goal
     * @param weightGoal users weight goal
     */
    public User(int id, String username, String password, String firstName, String lastName, Gender gender, String email, int height, LocalDate birthDate, double weight, int calorieGoal, double weightGoal) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.height = height;
        this.birthDate = birthDate;
        this.weight = weight;
        this.calorieGoal = calorieGoal;
        this.weightGoal = weightGoal;
    }

    /**
     * Returns users id
     * @return users id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns users username
     * @return users username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns users weight
     * @return users weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns users height
     * @return users height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns users first name
     * @return users first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns users last name
     * @return users last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns users email
     * @return users email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns users birthdate
     * @return users birthdate
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Returns gender of user
     * @return gender of user
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Calculates age by substracting birthdate year from current year
     * @return age of user
     */
    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    /**
     * Returns users calorie goal
     * @return users calorie goal
     */
    public int getCalorieGoal() {
        return calorieGoal;
    }

    /**
     * Returns users password
     * @return users password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns users weight goal
     * @return users weight goal
     */
    public double getWeightGoal() {
        return weightGoal;
    }
}


