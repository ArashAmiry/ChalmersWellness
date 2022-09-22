package com.example.chalmerswellness.ObjectModels;

import com.example.chalmerswellness.Controllers.ExerciseSet;

import java.util.List;
import java.util.Set;

public class ExerciseItem extends Exercise{

    private int id;

    private List<ExerciseItemSet> sets;

    public ExerciseItem(int id, Exercise exercise) {
        super(id, exercise.name, exercise.type, exercise.muscle, exercise.equipment, exercise.difficulty, exercise.instructions);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
