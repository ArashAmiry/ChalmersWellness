package com.example.chalmerswellness.ObjectModels;

public class Exercise {
    public int id;
    public String name;
    public String type;
    public String muscle;
    public String equipment;
    public String difficulty;
    public String instructions;

    public Exercise(int id, String name, String type, String muscle, String equipment, String difficulty, String instructions){
        this.id = id;
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    public Exercise(){

    }

    public int getId() {
        return id;
    }
}
