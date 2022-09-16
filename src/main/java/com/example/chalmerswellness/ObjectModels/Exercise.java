package com.example.chalmerswellness.ObjectModels;

public class Exercise {
    public String name;
    public String type;
    public String muscle;
    public String equipment;
    public String difficulty;
    public String instructions;

    public Exercise(String name, String type, String muscle, String equipment, String difficulty, String instructions){
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    public Exercise(){

    }
}
