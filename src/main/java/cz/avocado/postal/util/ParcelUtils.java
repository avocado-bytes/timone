package cz.avocado.postal.util;

import cz.avocado.postal.model.Parcel;

import java.util.Optional;

public class ParcelUtils {

    private static final String EXAMPLE = "E.g.: 54.2 46582";

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
    public static Optional<Parcel> processInput(String input) {
        if (input == null || input.isEmpty() || input.isBlank()) {
            return Optional.empty();
        }

        String[] values = input.split(" ");

        if (values.length != 2) {
            Utils.error(String.format("Incorrect number of values (%d). You are expected to enter exactly 2 values.", values.length));
            Utils.error(EXAMPLE);
            return Optional.empty();
        }

        float weight;
        try {
            weight = Float.parseFloat(values[0]);
        } catch (NumberFormatException e) {
            Utils.error(String.format("Incorrect weight parameter (%s). You are expected to provide numeric value.",
                    values[0]));
            Utils.error(EXAMPLE);
            return Optional.empty();
        }

        int parcelNumber;
        try {
            parcelNumber = Integer.parseInt(values[1]);
        } catch (NumberFormatException e) {
            Utils.error(String.format("Incorrect parcel parameter (%s). You are expected to provide integer value of five digits.",
                    values[1]));
            Utils.error(EXAMPLE);
            return Optional.empty();
        }

        if (values[1].length() != 5) {
            Utils.error(String.format("Incorrect parcel number length (%d). Parcel number should be 5 digits long integer",
                    values[1].length()));
            Utils.error(EXAMPLE);
            return Optional.empty();
        }

        return Optional.of(new Parcel(weight, parcelNumber));
    }

}
