package tests;


import taskBody.*;


import org.testng.annotations.*;
import utils.UtilsForTests;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static taskBody.Utils.mergeSortedFilesIntoOneFile;
import static org.assertj.core.api.Assertions.*;


public class TestMergeSortedFilesIntoOneFileMethod {

    private static final Logger LOGGER = Logger.getLogger(TestMergeSortedFilesIntoOneFileMethod.class.getName());

    @BeforeTest
    public void tearUp() {
        UtilsForTests.createFile("1", String.join("\n", "abc", "xyz"));
        UtilsForTests.createFile("2", String.join("\n", "def", "hij", "kix"));
        UtilsForTests.createFile("3", String.join("\n", "klm", "trololo", "xyz"));
    }

    @AfterTest
    public void tearDown() {
        for (File tempFile : new File(Constants.INPUT_FILE_PATH_FOR_TESTS.toString()).listFiles()) {
            if (tempFile.isFile()) {
                tempFile.delete();
            }
        }
    }

    @Test
    public void testFileMerge() {
        File folderWithFiles = new File(Constants.INPUT_FILE_PATH_FOR_TESTS.toString());
        List<File> sortedFiles = new ArrayList(Arrays.asList(folderWithFiles.listFiles()));
        Utils.makeDirectoryIfNotExist(Constants.OUTPUT_FILE_PATH_FOR_RESULT);
        File resultFileFromTest = new File(Constants.OUTPUT_FILE_PATH_FOR_RESULT.toString(), "result");
        mergeSortedFilesIntoOneFile(sortedFiles, resultFileFromTest);

        List<String> stringResults = new ArrayList<>();
        String line;
        try (BufferedReader br = Files.newBufferedReader(resultFileFromTest.toPath())) {
            while ((line = br.readLine()) != null) {
                stringResults.add(line);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "IO exception during reading from the result file from test");
        }
        assertThat(stringResults).isNotNull();
        assertThat(stringResults.size()).isEqualTo(8);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testEmptyList() {
        List<File> sortedFiles = new ArrayList<>();
        File resultFileFromTest = new File(Constants.INPUT_FILE_PATH_FOR_TESTS.toString(), "result");
        mergeSortedFilesIntoOneFile(sortedFiles, resultFileFromTest);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testWrongPath() {
        File folderWithFiles = new File(Constants.INPUT_FILE_PATH_FOR_TESTS.toString());
        List<File> sortedFiles = new ArrayList(Arrays.asList(folderWithFiles.listFiles()));
        File resultFileFromTest = new File(Constants.BROKEN_PATH.toString(), "result");
        mergeSortedFilesIntoOneFile(sortedFiles, resultFileFromTest);
    }
}