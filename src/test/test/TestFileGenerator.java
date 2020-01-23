package test;

import org.junit.Test;
import taskBody.Constants;
import taskBody.FileGenerator;

import static org.assertj.core.api.Assertions.*;

import java.io.File;

public class TestFileGenerator {

    @Test
    public void testCreateFileWithRandomContent() {
        File resultFile = FileGenerator.createFileWithRandomContent("resultForTest", Constants.MEMORY_SIZE_10MB);
        assertThat(resultFile).isNotNull();
        assertThat(resultFile.length()).isGreaterThan(Constants.MEMORY_SIZE_10MB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFileWithEmptyFileName() {
        File resultFile = FileGenerator.createFileWithRandomContent("", Constants.MEMORY_SIZE_10MB);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateFileWithInvalidMemorySize() {
        File resultFile = FileGenerator.createFileWithRandomContent("kokoko", 0L);
    }
}
