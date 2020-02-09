package cz.avocado.postal.util;

import cz.avocado.postal.model.Parcel;

import java.util.Optional;
import java.util.regex.Pattern;

public class ParcelUtils {

    private static final String EXAMPLE = "E.g.: 54.2 46582";

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
     * Expects a string input containing two values separated by a space.
     * First value will be a floating point value stating the weight of the parcel.
     * Second value will be also a numeric value of integer type being the parcel number.
     * If input is an empty String an empty Optional is returned.
     * If any of the values is invalid also an empty Optional is returned.
     * In any case a warning message is printed to notify the user the input is faulty.
     *
     * @param input
     * @return An empty optional if input was invalid. Populated if validation was successful.
     */
    public static Optional<Parcel> procesInput(String input) {
        if (input == null || input.isEmpty() || input.isBlank()) {
            return Optional.empty();
        }

        String[] values = input.split(" ");

        if (values.length != 2) {
            error(String.format("Incorrect number of values (%d). You are expected to enter exactly 2 values.", values.length));
            error(EXAMPLE);
            return Optional.empty();
        }

        float weight;
        try {
            weight = Float.valueOf(values[0]);
        } catch (NumberFormatException e) {
            error(String.format("Incorrect weight parameter (%s). You are expected to provide numeric value.",
                    values[0]));
            error(EXAMPLE);
            return Optional.empty();
        }

        Integer parcelNumber;
        try {
            parcelNumber = Integer.parseInt(values[1]);
        } catch (NumberFormatException e) {
            error(String.format("Incorrect parcel parameter (%s). You are expected to provide integer value of five digits.",
                    values[1]));
            error(EXAMPLE);
            return Optional.empty();
        }

        if (values[1].length() != 5) {
            error(String.format("Incorrect parcel number length (%d). Parcel number should be 5 digits long integer",
                    values[1].length()));
            error(EXAMPLE);
            return Optional.empty();
        }

        return Optional.of(new Parcel(weight, parcelNumber));
    }

    /**
     * Util method to print colored output to the cli.
     *
     * @param colorCode
     * @param value
     */
    public static void printMessage(String colorCode, String value) {
        System.out.println((char) 27 + colorCode + value);
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
