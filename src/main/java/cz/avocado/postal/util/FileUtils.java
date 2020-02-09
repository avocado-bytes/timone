package cz.avocado.postal.util;

import cz.avocado.postal.model.Parcel;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileUtils {

    /**
     * This utility method tries to find the file specified and parse it for Parcel objects.
     * If the file is not found an empty array is returned. If a faulty entry is found
     * an error message is printed and the parsing process continues skipping the faulty entry.
     *
     * @param filePath
     * @return
     */
    public static Stream<Parcel> readFile(String filePath) {

        return readFileByLines(filePath).stream()
                .map(ParcelUtils::sanitizeInput)
                .map(ParcelUtils::processInput)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    /**
     * This method reads the file contents into a string array.
     * Each array entry represents a line of text in the file.
     * If file is not found, warning message is printed and an empty array is returned.
     *
     * @param filePath
     * @return
     */
    private static List<String> readFileByLines(String filePath) {
        BufferedReader reader;
        try {
            var file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            ParcelUtils.error(String.format("Could not find file: %s.", filePath));
            return Collections.emptyList();
        }

        List<String> lines = new ArrayList<>();

        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                ParcelUtils.error("Error reading file line.");
                continue;
            }
            lines.add(line);
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            ParcelUtils.error("Error closing file. Possible memory leak.");
        }

        return lines;
    }

}
