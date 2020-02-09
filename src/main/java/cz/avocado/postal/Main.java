package cz.avocado.postal;

import cz.avocado.postal.model.Parcel;
import cz.avocado.postal.util.FileUtils;
import cz.avocado.postal.util.ParcelUtils;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String EXIT_KEYWORD = "quit";
    private static final String LIST_KEYWORD = "list";
    private static List<Parcel> inputs = new ArrayList<>();

    public static void main(String[] args) {
        cliOptionsHandling(args);

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

            Optional<Parcel> parcel = ParcelUtils.processInput(ParcelUtils.sanitizeInput(input));
            if (parcel.isEmpty()) {
                ParcelUtils.error("Input was incorrect. The data will not be persisted");
                continue;
            }
            ParcelUtils.info("Parcel saved, type 'list' to see saved items");
            inputs.add(parcel.get());
        }

    }

    /**
     * This utility method parses the command line arguments and applies all required actions accordingly
     * @param args
     */
    private static final void cliOptionsHandling(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption(
                new Option("p", "parcels", true,
                        "Specify an input file to initially import parcels from. The format of information in the file" +
                                "must be the same that is used for manual input. In case of faulty information an error message " +
                                "will be printed and the faulty entry will not be persisted.")
        );

        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            String filePath = cmd.getOptionValue('p');
            ParcelUtils.info(String.format("Starting import from file: %s...", filePath));
            FileUtils.readFile(filePath).forEach(parcel -> inputs.add(parcel));
            ParcelUtils.info("Initial import done.");
        } catch (ParseException e) {
            formatter.printHelp("Timone Parcel Utility", options);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
