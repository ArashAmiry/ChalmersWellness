package com.example.chalmerswellness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalorieCalculatorTest {

    @Test
    void higherActivityLevelShouldResultInHigherIntake() {
        int higherActivityIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 2, 1);
        int lowerActivityIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 1, 1);
        Assertions.assertTrue(higherActivityIntake > lowerActivityIntake);
    }

    @Test
    void higherCalorieDeltaShouldResultInHigherIntake() {
        int higherCalorieDeltaIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 2, 1);
        int lowerCalorieDeltaIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 2, 0);
        Assertions.assertTrue(higherCalorieDeltaIntake > lowerCalorieDeltaIntake);
    }

    @Test
    void tallerUserShouldResultInHigherIntake() {
        int tallerUserIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 2, 1, 1, 1);
        int shorterUserIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 1, 1);
        Assertions.assertTrue(tallerUserIntake > shorterUserIntake);
    }

    @Test
    void heavierUserShouldResultInHigherIntake() {
        int heavierUserIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 2, 1, 1, 1, 1);
        int lighterUserIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 1, 1);
        Assertions.assertTrue(heavierUserIntake > lighterUserIntake);
    }

    @Test
    void youngerUserShouldResultInHigherIntake() {
        int olderUserIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 2, 1, 1);
        int youngerUserIntake = CalorieCalculator.calculateCalorieIntake(Gender.MALE, 1, 1, 1, 1, 1);
        Assertions.assertTrue(youngerUserIntake > olderUserIntake);
    }
}
