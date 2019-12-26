package test;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import taskBody.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static taskBody.Utils.mergeSortedFilesIntoOneFile;

public class TestMergeSortedFilesIntoOneFileMethod {

    @Before
    public void tearUp() throws IOException {
        createFile("1", String.join("\n", "abc", "xyz"));
        createFile("2", String.join("\n", "def", "hij"));
        createFile("3", String.join("\n", "klm", "xyz"));
    }

    @Test
    public void testFileMerge() throws IOException {
        File folderWithFiles = new File(Constants.inputFilePathForTests.toString());
        List<File> sortedFiles = new ArrayList(Arrays.asList(folderWithFiles.listFiles()));
        File resultFileFromTest = new File(Constants.outputFilePathForResult.toString(), "result");
        mergeSortedFilesIntoOneFile(sortedFiles, resultFileFromTest);

        List<String> stringResults = new ArrayList<String>();
        String line;
        try (BufferedReader br = Files.newBufferedReader(resultFileFromTest.toPath())) {
            while ((line = br.readLine()) != null) {
                stringResults.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertThat(stringResults, IsNull.notNullValue());
        Assert.assertThat(stringResults.size(), Is.is(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyList() throws IOException {
        List<File> sortedFiles = new ArrayList<>();
        File resultFileFromTest = new File(Constants.outputFilePathForResult.toString(), "result");
        mergeSortedFilesIntoOneFile(sortedFiles, resultFileFromTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongPath() throws IOException {
        File folderWithFiles = new File(Constants.inputFilePathForTests.toString());
        List<File> sortedFiles = new ArrayList(Arrays.asList(folderWithFiles.listFiles()));
        File resultFileFromTest = new File(Constants.brokenPath.toString(), "result");
        mergeSortedFilesIntoOneFile(sortedFiles, resultFileFromTest);
    }

    private File createFile(String fileName, String fileContent) {
        File file = Paths.get(Constants.inputFilePathForTests.toString(), fileName).toFile();
        file.deleteOnExit();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}