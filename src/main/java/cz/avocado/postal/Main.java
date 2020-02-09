package cz.avocado.postal;

import cz.avocado.postal.model.Fees;
import cz.avocado.postal.model.Parcel;
import cz.avocado.postal.util.FileUtils;
import cz.avocado.postal.util.ParcelUtils;
import cz.avocado.postal.util.Utils;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String EXIT_KEYWORD = "quit";
    private static final String LIST_KEYWORD = "list";
    private static List<Parcel> inputs = new ArrayList<>();
    private static Fees fees = new Fees();

    public static void main(String[] args) {
        cliOptionsHandling(args);

        var scanner = new Scanner(System.in);

        Utils.info("Waiting for package input. To end program type 'quit'");

        while (true) {
            var input = scanner.nextLine();

            if (input.equalsIgnoreCase(EXIT_KEYWORD)) {
                break;
            }

            if (input.equalsIgnoreCase(LIST_KEYWORD)) {
                inputs.forEach(it -> Utils.info(it.toString()));
                continue;
            }

            Optional<Parcel> parcel = ParcelUtils.processInput(Utils.sanitizeInput(input));
            if (parcel.isEmpty()) {
                Utils.error("Input was incorrect. The data will not be persisted");
                continue;
            }

            var par = parcel.get();
            par.setFee(fees.getFee(par.getPackageWeight()));
            Utils.info("Parcel saved, type 'list' to see saved items");
            inputs.add(par);
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

        options.addOption(
                new Option("f", "fees", true,
                        "Specify the path to the input file where fees for parcel weights are defined.")
        );

        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            String filePath = cmd.getOptionValue('p');
            String feesFilePath = cmd.getOptionValue('f');
            if (feesFilePath != null && !feesFilePath.isEmpty()) {
                Utils.info(String.format("Starting fee configuration import from file: %s...", feesFilePath));
                fees = FileUtils.readFeesFile(feesFilePath);
                Utils.info("Fees import done.");
            } else {
                fees = new Fees();
            }

            Utils.info(String.format("Starting import from file: %s...", filePath));
            FileUtils.readInitializationFile(filePath).forEach(parcel -> {
                parcel.setFee(fees.getFee(parcel.getPackageWeight()));
                inputs.add(parcel);
            });
            Utils.info("Initial import done.");

        } catch (ParseException e) {
            formatter.printHelp("Timone Parcel Utility", options);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
