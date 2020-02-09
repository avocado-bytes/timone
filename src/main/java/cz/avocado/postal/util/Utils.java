package cz.avocado.postal.util;

import java.util.regex.Pattern;

public class Utils {

    /**
     * Sanitize function serves to clean the input.
     * Surrounding whitespace is trimmed and duplicated
     * whitespace within the input is also reduced to one space character.
     * If null is passed in an empty string is returned for null safety purposes.
     *
     * @param input
     * @return
     */
    public static String sanitizeInput(String input) {
        if (input == null || input.isBlank() || input.isEmpty()) {
            return "";
        }
        input = Pattern.compile("\\s+").matcher(input).replaceAll(" ");
        return input.trim();
    }

    /**
     * Util method to print colored output to the cli.
     *
     * @param colorCode
     * @param value
     */
    public static void printMessage(String colorCode, String value) {
        System.out.println((char) 27 + colorCode + value + (char) 27 + "[0m");
    }

    /**
     * Util method to print colored output to the cli.
     * Prints in red color.
     *
     * @param errorMessage
     */
    public static void error(String errorMessage) {
        printMessage("[31m", errorMessage);
    }

    /**
     * Util method to print colored output to the cli.
     * Prints in blue color.
     *
     * @param infoMessage
     */
    public static void info(String infoMessage) {
        printMessage("[34m", infoMessage);
    }

}
