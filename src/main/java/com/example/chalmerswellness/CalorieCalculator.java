package com.example.chalmerswellness;

public class CalorieCalculator {
    private static double calculateBasalMetabolicRate(Gender gender, double kg, double cm, int years) {
        double basalMetabolicRate;
        if (gender == Gender.MALE) {
            basalMetabolicRate = 66.47 + (13.75 * kg) + (5.003 * cm) - (6.755 * years);
        } else {
            basalMetabolicRate = 655.1 + (9.563 * kg) + (1.85 * cm) - (4.676 * years);
        }
        return basalMetabolicRate;
    }

    private static double calculateActiveMetabolicRate(double basalMetabolicRate, double activityLevel) {
        return basalMetabolicRate * activityLevel;
    }

    /**
     * Calculates amount of calories needed to sustain weight and adds the calorie delta
     * @param gender gender of user
     * @param kg weight of user in kilograms
     * @param cm height of user in centimeters
     * @param years age of user in years
     * @param activityLevel number ranging from 1,2 to 1,9 representing how active the user is
     * @param calorieDelta number of calories to add or subtract from the calories needed to sustain weight
     * @return amount of calories needed to sustain weight plus the calorie delta
     */
    public static int calculateCalorieIntake(Gender gender, double kg, double cm, int years, double activityLevel, int calorieDelta) {
        double basalMetabolicRate = calculateBasalMetabolicRate(gender, kg, cm, years);
        double activeMetabolicRate = calculateActiveMetabolicRate(basalMetabolicRate, activityLevel);
        return (int) Math.round(activeMetabolicRate) + calorieDelta;
    }
}
