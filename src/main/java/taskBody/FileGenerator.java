package taskBody;


import io.qameta.allure.Step;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static taskBody.Constants.MAX_LENGTH_OF_STRING_IN_TEST_FILE;
import static taskBody.Constants.MIN_LENGTH_OF_STRING_IN_TEST_FILE;

public class FileGenerator {

    private static final Logger LOGGER = Logger.getLogger(FileGenerator.class.getName());

    @Step
    public static File createFileWithRandomContent(String fileName, long memorySize) {

        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("The file name cannot be empty. Please make sure that name of generated file is correct");
        }

        if (memorySize <= 0) {
            throw new IllegalArgumentException("The memory size of file to be generated and sorted is less or equal 0. Please make sure that size of generated file is correct");
        }
        Utils.makeDirectoryIfNotExist(Constants.INPUT_FILE_PATH);

        File file = Paths.get(Constants.INPUT_FILE_PATH.toString(), fileName).toFile();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            while (file.length() <= memorySize)
                bw.write(getAlphaNumericString() + System.lineSeparator());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The error happens during writing a file which is going to be sorted");
        }
        return file;
    }

    private static String getAlphaNumericString() {
        int stringLength = MIN_LENGTH_OF_STRING_IN_TEST_FILE + (int) (Math.random() * MAX_LENGTH_OF_STRING_IN_TEST_FILE);
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
