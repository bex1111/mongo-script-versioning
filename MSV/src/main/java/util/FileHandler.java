package util;

import exception.MSVExceptionFactory;
import lombok.experimental.UtilityClass;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@UtilityClass
public class FileHandler {
    public static String readLineByLine(String filePath, String fileName) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath + File.separator + fileName), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            throw MSVExceptionFactory.cannotReadFile(filePath, e);
        }
        return contentBuilder.toString();
    }


    public static void writeString(String text, String stringPath) {
        Path path = Paths.get(stringPath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(text);
        } catch (IOException e) {
            throw MSVExceptionFactory.cannotWriteFile(stringPath, e);
        }
    }
}
