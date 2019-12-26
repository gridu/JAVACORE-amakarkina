package taskBody;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File file = new File(Constants.inputFilePath.toString(), "testData1");
        File fileOut = new File(Constants.outputFilePathForTempFiles.toString());
        File resultFile = new File(Constants.outputFilePathForResult.toString(), "result");

        List<File> files = Utils.split(file, Constants.memorySize, fileOut);
        Utils.mergeSortedFilesIntoOneFile(files, resultFile);

        System.out.println("Sorted file path is " + Constants.outputFilePathForResult.toString() + "/result");
    }
}