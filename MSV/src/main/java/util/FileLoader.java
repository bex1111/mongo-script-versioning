package util;

import exception.MSVException;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@UtilityClass
public class FileLoader {
    public static String readLineByLine(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            throw new MSVException("Cannot read file. (Path: " + filePath + ")", e);
        }
        return contentBuilder.toString();
    }
}
