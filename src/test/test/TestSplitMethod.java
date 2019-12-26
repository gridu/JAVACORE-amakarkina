package test;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import taskBody.Constants;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import static taskBody.Utils.split;

public class TestSplitMethod {

    @After
    public void tearDown() {
        for (File tempFile : new File(Constants.outputFilePathForTempFiles.toString()).listFiles())
            if (tempFile.isFile()) tempFile.delete();
    }

    @Test
    public void testFileSplit() throws Exception {
        List<File> files = split(new File(Constants.inputFilePath.toString(), "testData1"), Constants.memorySize, new File(Constants.outputFilePathForTempFiles.toString()));
        Assert.assertThat(files, IsNull.notNullValue());
        Assert.assertThat("Temp files number", files.size(), Is.is(5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileIsNotFound() throws Exception {
        split(new File(Constants.inputFilePath.toString(), "123321.txt"), Constants.memorySize, new File(Constants.outputFilePathForTempFiles.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tempDirectoryIsNotFound() throws Exception {
        split(new File(Constants.inputFilePath.toString(), "testData1"), Constants.memorySize, new File("src/kokoko/"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongMemorySize() throws Exception {
        split(new File(Constants.inputFilePath.toString(), "testData1"), 0, new File(Constants.outputFilePathForTempFiles.toString()));
    }
}