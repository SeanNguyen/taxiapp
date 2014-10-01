package com.taxi123.taxi123android;

/**
 * Created by Storm on 9/28/2014.
 */
public class Utilities {
    public static int convertStringToInt (String input) {
        try {
            int result = Integer.parseInt(input);
            return result;
        } catch (Exception e) {
            return -1;
        }
    }

    public static double convertStringToDouble (String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            return -1;
        }
    }
}
