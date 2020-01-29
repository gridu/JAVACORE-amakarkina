package tests;

import org.testng.annotations.AfterTest;
import taskBody.Constants;
import taskBody.FileGenerator;
import org.testng.annotations.Test;


import java.io.File;
import static org.assertj.core.api.Assertions.assertThat;
import static taskBody.Constants.longFileName;

public class TestFileGenerator {

    @AfterTest
    public void tearDown() {
        for (File tempFile : new File(Constants.INPUT_FILE_PATH.toString()).listFiles()) {
            if (tempFile.isFile()) {
                tempFile.delete();
            }
        }
    }

    @Test
    public void testCreateFileWithRandomContent() {
        File resultFile = FileGenerator.createFileWithRandomContent("resultForTest", Constants.MEMORY_SIZE_10MB);
        assertThat(resultFile).isNotNull();
        assertThat(resultFile.length()).isGreaterThan(Constants.MEMORY_SIZE_10MB);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testCreateFileWithRandomContentWithLongFileName() {
        FileGenerator.createFileWithRandomContent(longFileName, Constants.MEMORY_SIZE_10MB);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testCreateFileWithEmptyFileName() {
        FileGenerator.createFileWithRandomContent("", Constants.MEMORY_SIZE_10MB);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testCreateFileWithZeroMemorySize() {
        FileGenerator.createFileWithRandomContent("kokoko", 0L);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void testCreateFileWithNegativeMemorySize() {
        FileGenerator.createFileWithRandomContent("kokoko", -1L);
    }
}