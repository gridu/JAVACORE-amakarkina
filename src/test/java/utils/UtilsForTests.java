package utils;

import taskBody.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtilsForTests {

    private static final Logger LOGGER = Logger.getLogger(UtilsForTests.class.getName());

    public static File createFile(String fileName, String fileContent) {
        Utils.makeDirectoryIfNotExist(Constants.INPUT_FILE_PATH_FOR_TESTS);
        File file = Paths.get(Constants.INPUT_FILE_PATH_FOR_TESTS.toString(), fileName).toFile();
        file.deleteOnExit();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(fileContent);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The error happens during writing test splitted files");
        }
        return file;
    }
}
