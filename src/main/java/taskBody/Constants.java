package taskBody;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static Path inputFilePathForTests = Paths.get("src/main/resources/sortFilesForTests");
    public static Path inputFilePath = Paths.get("src/main/resources/testData");
    public static Path outputFilePathForTempFiles = Paths.get("src/main/resources/temp");
    public static Path outputFilePathForResult = Paths.get("src/main/resources/result");
    public static Path brokenPath = Paths.get("src/main/resources/brokenPath");
    public static long memorySize = 268435456; // 256 mb
}