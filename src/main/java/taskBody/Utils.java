package taskBody;

import io.qameta.allure.Step;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    @Step
    public static List<File> split(File file, long memorySize) {
        if (memorySize <= 0) {
            throw new IllegalArgumentException("Memory size should be more than zero!");
        }

        if (!file.exists()) {
            throw new IllegalArgumentException("There is no file to be sorted!");
        }

        Utils.makeDirectoryIfNotExist(Constants.OUTPUT_FILE_PATH_FOR_TEMP_FILES);
        File directoryToSave = new File(Constants.OUTPUT_FILE_PATH_FOR_TEMP_FILES.toString());

        List<File> splittedFiles = new ArrayList<>();
        long maxSplittedFileSize = memorySize / 2;
        List<String> lines = new ArrayList<>();
        String line = "";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            while (line != null) {
                long stringSize = 0;
                while ((stringSize < maxSplittedFileSize) && ((line = reader.readLine()) != null)) {
                    lines.add(line);
                    stringSize += line.getBytes().length;
                }

                Collections.sort(lines);
                splittedFiles.add(saveListToFile(lines, directoryToSave));
                lines.clear();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Something goes wrong. Please make sure that there are: \n" +
                    "1. Test file which contains string for sorting with name testData1 in the folder src/main/resouces/testData \n" +
                    "2. There are folders: result, sortFilesForTests amd temp in the src/main/resources \n");
        }
        return splittedFiles;
    }

    private static File saveListToFile(List<String> lines, File directoryToSave) {
        File sortedTempFile = null;
        try {
            sortedTempFile = File.createTempFile("temp", ".txt", directoryToSave);
            BufferedWriter bw = new BufferedWriter(new FileWriter(sortedTempFile, false));
            for (String s : lines) {
                bw.write(s + System.lineSeparator());
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to write to sorted temp file");
        }
        return sortedTempFile;
    }

    @Step
    public static void mergeSortedFilesIntoOneFile(List<File> files, File resultFile) {
        if (files.size() == 0) {
            throw new IllegalArgumentException("There are no files to merge. Quantity files is 0.");
        }

        Map<BufferedReader, String> stringFromFile = new HashMap<>();
        List<BufferedReader> readers = new ArrayList<>();

        if (resultFile.exists()) {
            resultFile.delete();
        }

        try {
            resultFile.createNewFile();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Something goes wrong. Please make sure that there is folder src/main/resources/result");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile, true))) {
            for (File file : files) {
                file.deleteOnExit();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                readers.add(reader);
                String line;
                if ((line = reader.readLine()) != null) {
                    stringFromFile.put(reader, line);
                }
            }
            List<String> stringsToSort = new ArrayList<>(stringFromFile.values());
            while (stringsToSort.size() > 0) {
                Collections.sort(stringsToSort);
                String lineToWrite = stringsToSort.remove(0);
                bw.append(lineToWrite);
                bw.append("\n");
                BufferedReader reader = getKeyByValue(stringFromFile, lineToWrite);
                stringFromFile.values().remove(lineToWrite);
                String nextLine;
                if ((nextLine = reader.readLine()) != null) {
                    stringFromFile.put(reader, nextLine);
                    stringsToSort.add(nextLine);
                }
            }

            for (BufferedReader reader : readers) {
                reader.close();
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Unable to write to result file");
        }
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void makeDirectoryIfNotExist(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Unable to create directories for path: " + path.toString());
            }
        }
    }
}