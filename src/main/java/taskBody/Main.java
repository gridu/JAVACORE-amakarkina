package taskBody;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        File file = FileGenerator.createFileWithRandomContent("testData1", Constants.MEMORY_SIZE);
        List<File> files = Utils.split(file, Constants.MEMORY_SIZE);

        Utils.makeDirectoryIfNotExist(Constants.OUTPUT_FILE_PATH_FOR_RESULT);
        File resultFile = new File(Constants.OUTPUT_FILE_PATH_FOR_RESULT.toString(), "result");
        Utils.mergeSortedFilesIntoOneFile(files, resultFile);

        LOGGER.info("Sorted file path is " + Constants.OUTPUT_FILE_PATH_FOR_RESULT.toString() + "/result");
    }
}