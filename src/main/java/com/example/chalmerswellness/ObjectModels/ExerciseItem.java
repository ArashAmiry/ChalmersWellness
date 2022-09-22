package com.example.chalmerswellness.ObjectModels;

public class ExerciseItem extends Exercise{

    private int id;

    public ExerciseItem(int id, Exercise exercise) {
        super(id, exercise.name, exercise.type, exercise.muscle, exercise.equipment, exercise.difficulty, exercise.instructions);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
