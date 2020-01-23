package test;

import taskBody.Constants;
import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import static taskBody.Utils.split;

public class TestSplitMethod {

    @After
    public void tearDown() {
        for (File tempFile : new File(Constants.OUTPUT_FILE_PATH_FOR_TEMP_FILES.toString()).listFiles()) {
            if (tempFile.isFile()) {
                tempFile.delete();
            }
        }
    }

    @Test
    public void testFileSplit() {
        List<File> files = split(new File(Constants.INPUT_FILE_TO_BE_SORTED_PATH_FOR_TESTS.toString(), "testData1"), Constants.MEMORY_SIZE_10MB);
        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(2);
    }

    @Test
    public void testFileSplitForMemorySizeBiggerThanFileSize() throws Exception {
        List<File> files = split(new File(Constants.INPUT_FILE_TO_BE_SORTED_PATH_FOR_TESTS.toString(), "testData1"), Constants.MEMORY_SIZE);
        assertThat(files).isNotNull();
        assertThat(files.size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileIsNotFound() throws Exception {
        split(new File(Constants.INPUT_FILE_PATH.toString(), "123321.txt"), Constants.MEMORY_SIZE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongMemorySize() throws Exception {
        split(new File(Constants.INPUT_FILE_PATH.toString(), "testData1"), 0);
    }
}