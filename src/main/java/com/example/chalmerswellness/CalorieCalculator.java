package com.example.chalmerswellness;

public class CalorieCalculator {
    private static double calculateBasalMetabolicRate(Gender gender, double kg, double cm, int years) {
        double BMR;
        if (gender == Gender.Male) {
            BMR = 66.47 + (13.75 * kg) + (5.003 * cm) - (6.755 * years);
        } else {
            BMR = 655.1 + (9.563 * kg) + (1.85 * cm) - (4.676 * years);
        }
        return BMR;
    }

    private static double calculateActiveMetabolicRate(double BMR, double activityLevel) {
        return BMR * activityLevel;
    }

    public static int calculateCalorieIntake(Gender gender, double kg, double cm, int years, double activityLevel, int calorieDelta) {
        double BMR = calculateBasalMetabolicRate(gender, kg, cm, years);
        double AMR = calculateActiveMetabolicRate(BMR, activityLevel);
        return (int) Math.round(AMR) + calorieDelta;
    }
}
