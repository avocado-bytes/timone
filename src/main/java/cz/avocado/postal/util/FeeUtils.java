package cz.avocado.postal.util;

import cz.avocado.postal.model.Pair;
import cz.avocado.postal.model.Parcel;

import java.util.Optional;
import java.util.regex.Pattern;

public class FeeUtils {

    private static final String EXAMPLE = "E.g.: 54.2 32.8";

    /**
     * Expects a string input containing two values separated by a space.
     * First value will be a floating point value stating the fee limit weight.
     * Second value will be also float value describing the fee applicable.
     * If input is an empty String an empty Optional is returned.
     * If any of the values is invalid also an empty Optional is returned.
     * In any case a warning message is printed to notify the user the input is faulty.
     *
     * @param input
     * @return An empty optional if input was invalid. Populated if validation was successful.
     */
    public static Optional<Pair<Float, Float>> processInput(String input) {
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

        float fee;
        try {
            fee = Float.parseFloat(values[1]);
        } catch (NumberFormatException e) {
            Utils.error(String.format("Incorrect fee parameter (%s). You are expected to provide a floating point numeric value",
                    values[1]));
            Utils.error(EXAMPLE);
            return Optional.empty();
        }

        return Optional.of(new Pair(weight, fee));
    }

}
