package reader;


import exception.MSVException;
import lombok.Data;
import reader.dto.FileJsDto;
import reader.dto.FileJsonDto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static util.Constans.JSONTYPE;
import static util.Constans.JSTYPE;
import static util.Logger.log;
import static validator.ValidatorFiles.fileJsValidator;
import static validator.ValidatorFiles.fileJsonValidator;

@Data
public class FileReader {

    List<String> fileNames;
    List<FileJsDto> fileJsDtoList;
    List<FileJsonDto> fileJsonDtoList;

    public FileReader(String path) {
        fileNames = getFileList(path);
        initFileLists(path);
    }

    private List<String> getFileList(String path) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString())
                    .collect(toList());
        } catch (IOException e) {
            log().info(e.getMessage());
            return new ArrayList<>();
        }
    }

    private void initFileLists(String path) {
        fileJsDtoList = new ArrayList<>();
        fileJsonDtoList = new ArrayList<>();
        fileNames.forEach(x -> {
            if (x.contains(JSONTYPE)) {
                String[] fileValues = x.replace(JSONTYPE, "").split("_");
                fileJsonDtoList.add(fileJsonValidator(fileValues, x, readLineByLine(path + File.separator + x)));
            } else if (x.contains(JSTYPE)) {
                String[] fileValues = x.replace(JSTYPE, "").split("_");
                fileJsDtoList.add(fileJsValidator(fileValues, x, readLineByLine(path + File.separator + x)));
            } else {
                log().warn("Not used files: " + x);
            }
        });
    }

    private static String readLineByLine(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        } catch (IOException e) {
            throw new MSVException("Cannot read file. (Path: " + filePath + ")", e);
        }

        return contentBuilder.toString();
    }
}
