package controllers;

import models.Member;
import models.Trainer;
import models.Assessment;

/**
 * @version 1
 * @title GymUtility
 * @Au Sheamus Clifford
 * @discription A utility class (or helper class) is a "structure" that has only static methods and encapsulates no
 * state (fields). Typically the functionality of the methods is reusable across a range of applications.
 */

public class GymUtility {

    /**@title calculateBMI
     * @Au Sheamus Clifford
     * @discription This method calculates the BMI of a member bassed on most recent assessment or details entered when
     * registered if no assessment is recorded yet
     * @param member type Member
     * @param assessment type Assessment
     * @return type double: calculated BMI for member
     */
    public static double calculateBMI(Member member, Assessment assessment) {
        double bmi;

        if (assessment == null) {
            bmi = member.getStartWeight() / member.getHeight() / member.getHeight();
        } else {
            bmi = assessment.getWeight() / member.getHeight() / member.getHeight();
        }

        return roundAvoid(bmi,2);
    }

    /**@title determineBMICategory
     * @Au Sheamus Clifford
     * @discription Calculates what category a member falls into with a specific BMI
     * @param bmiValue type double
     * @return type String: calculated BMI category based BMI param
     */
    public static String determineBMICategory(double bmiValue) {
        if (bmiValue < 16) return "SEVERELY UNDERWEIGHT";
        else if (bmiValue >= 16 && bmiValue < 18.5) return "UNDERWEIGHT";
        else if (bmiValue >= 18.5 && bmiValue < 25) return "NORMAL";
        else if (bmiValue >= 25 && bmiValue < 30) return "OVERWEIGHT";
        else if (bmiValue >= 30 && bmiValue < 35) return "MODERATELY OBESE";
        else if (bmiValue >= 35) return "SEVERELY OBESE";
        else return null;
    }

    /**@title isIdealBodyWeight
     * @Au Sheamus Clifford
     * @discription checks if member is at ideal bodyweight for their height based on a calculation
     * For males, an ideal body weight is: 50 kg + 2.3 kg for each inch over 5 feet.
     * For females, an ideal body weight is: 45.5 kg + 2.3 kg for each inch over 5 feet.
     * Note: if no gender is specified, return the result of the female calculation.
     * Note: if the member is 5 feet or less, return 50kg for male and 45.5kg for female
     * To allow for different calculations and rounding, when testing for the ideal body weight, if it is +/- .2 of
     * the devine formula, return true
     * @param member type Member
     * @param assessment type Assessment
     * @return boolean value if member is at ideal body weight
     */
    public static boolean isIdealBodyWeight(Member member, Assessment assessment) {
        double heightInmm = member.getHeight() * 1000;
        double mmAboveFiveFeet = heightInmm - 1524;//5ft in mm = 1524
        mmAboveFiveFeet = mmAboveFiveFeet / 25.4;               //mm per inch

        double lowerBracket;
        double upperBracket;
        boolean result = true;

        if (member.getGender().equalsIgnoreCase("male")) {
            mmAboveFiveFeet = (mmAboveFiveFeet * 2.3) + 50;
            lowerBracket = mmAboveFiveFeet - 0.2;
            upperBracket = mmAboveFiveFeet + 0.2;
            if (assessment == null) {
                if (member.getStartWeight() < lowerBracket || member.getStartWeight() > upperBracket) result = false;
            } else {
                if (assessment.getWeight() < lowerBracket || assessment.getWeight() > upperBracket) result = false;
            }
        } else if (member.getGender().equalsIgnoreCase("female") || member.getGender().equals("Unspecified") ) {
            mmAboveFiveFeet = (mmAboveFiveFeet * 2.3) + 45.5;
            lowerBracket = mmAboveFiveFeet - 0.2;
            upperBracket = mmAboveFiveFeet + 0.2;
            if (assessment == null) {
                if (member.getStartWeight() < lowerBracket || member.getStartWeight() > upperBracket) result = false;
            } else {
                if (assessment.getWeight() < lowerBracket || assessment.getWeight() > upperBracket) result = false;
            }
        }
        return result;
    }

    /**
     * @param value  type float:value to be rounded
     * @param places type int: amount of decimal places to round float value to
     * @return type double: returns rounded float value as a double
     * @title roundAvoid
     * @Au copied from stackOverflow
     */
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

}
