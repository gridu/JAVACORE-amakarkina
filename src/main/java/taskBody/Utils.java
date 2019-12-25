package taskBody;

import java.io.*;
import java.util.*;

public class Utils {

    public static List<File> split(File file, long memorySize, File directoryToSave) throws Exception {
        if (memorySize <= 0) {
            throw new IllegalArgumentException("Memory size should be more than zero!");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        long maxSplittedFileSize = memorySize / 2;
        List<File> splittedFiles = new ArrayList<>();

        try {
            List<String> lines = new ArrayList<>();
            String line = "";
            try {
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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            reader.close();
        }
        return splittedFiles;
    }


    private static File saveListToFile(List<String> lines, File directoryToSave) throws IOException {
        File sortedTempFile = File.createTempFile("temp", ".txt", directoryToSave);
        OutputStream out = new FileOutputStream(sortedTempFile);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            for (String s : lines) {
                writer.write(s);
                writer.newLine();
            }
        }
        return sortedTempFile;
    }

    public static void mergeSortedFilesIntoOneFile(List<File> files, File resultFile) throws IOException {
        if (files.size() == 0) {
            throw new IllegalArgumentException("There are no files to merge. Quantity files is 0.");
        }

        Map<BufferedReader, String> stringFromFile = new HashMap<>();
        List<BufferedReader> readers = new ArrayList<>();

        if (resultFile.exists()) {
            resultFile.delete();
        }
        resultFile.createNewFile();

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
            throw new RuntimeException(e);
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
}