package com.example.chalmerswellness.ObjectModels;

public class Exercise {
    private final int id;
    private final String name;
    private final String type;
    private final String muscle;
    private final String equipment;
    private final String difficulty;
    private final String instructions;
    private int sets_count;

    public Exercise(int id, String name, String type, String muscle, String equipment, String difficulty, String instructions){
        this.id = id;
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    public Exercise(int id, Exercise exercise){
        this.id = id;
        this.name = exercise.getName();
        this.type =  exercise.getType();
        this.muscle = exercise.getMuscle();
        this.equipment = exercise.getEquipment();
        this.difficulty = exercise.getDifficulty();
        this.instructions = exercise.getInstructions();
    }

    public void setSets(int sets){
        this.sets_count = sets;
    }

    public int getSets(){
        return sets_count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getInstructions() {
        return instructions;
    }
}
