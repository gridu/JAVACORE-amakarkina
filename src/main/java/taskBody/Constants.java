package taskBody;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
    public static final Path INPUT_FILE_PATH = Paths.get("src/main/resources/testData");
    public static final Path OUTPUT_FILE_PATH_FOR_TEMP_FILES = Paths.get("src/main/resources/temp");
    public static final Path OUTPUT_FILE_PATH_FOR_RESULT = Paths.get("src/main/resources/result");
    public static final Path BROKEN_PATH = Paths.get("src/main/resources/BROKEN_PATH");
    public static final Path INPUT_FILE_TO_BE_SORTED_PATH_FOR_TESTS = Paths.get("src/test/testData/input");
    public static final Path INPUT_FILE_PATH_FOR_TESTS = Paths.get("src/test/testData/splittedFiles");

    public static final long MEMORY_SIZE = 268435456L; // 256 mb
    public static final long MEMORY_SIZE_10MB = 10485760L; // 10 mb

    public static final int MIN_LENGTH_OF_STRING_IN_TEST_FILE = 10;
    public static final int MAX_LENGTH_OF_STRING_IN_TEST_FILE = 20;
}