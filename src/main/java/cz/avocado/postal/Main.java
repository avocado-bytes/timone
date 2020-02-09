package cz.avocado.postal;

import cz.avocado.postal.model.Parcel;
import cz.avocado.postal.util.ParcelUtils;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String EXIT_KEYWORD = "quit";
    private static final String LIST_KEYWORD = "list";

    public static void main(String[] args) {

        var inputs = new ArrayList<Parcel>();

        var scanner = new Scanner(System.in);

        ParcelUtils.info("Waiting for package input. To end program type 'quit'");

        while (true) {
            var input = scanner.nextLine();

            if (input.equalsIgnoreCase(EXIT_KEYWORD)) {
                break;
            }

            if (input.equalsIgnoreCase(LIST_KEYWORD)) {
                inputs.forEach(it -> ParcelUtils.info(it.toString()));
                continue;
            }

            Optional<Parcel> parcel = ParcelUtils.procesInput(ParcelUtils.sanitizeInput(input));
            if (parcel.isEmpty()) {
                ParcelUtils.error("Input was incorrect. The data will not be persisted");
                continue;
            }
            ParcelUtils.info("Parcel saved, type 'list' to see saved items");
            inputs.add(parcel.get());
        }

    }

}
