package com.taxi123.taxi123android;

import java.text.DecimalFormat;

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

    public static  String convertDistanceToString (double distance) {
        if (distance < 0) {
            return "...";
        } else {
            String value = new DecimalFormat("##.##").format(distance / 1000);
            value += " km";
            return value;
        }
    }
}
