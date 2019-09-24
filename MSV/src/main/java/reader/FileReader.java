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

    private List<String> fileNames;
    private List<FileJsDto> fileJsDtoList;
    private List<FileJsonDto> fileJsonDtoList;
    private String fileLocation;


    public FileReader(String fileLocation) {
        this.fileLocation = fileLocation;
        fileNames = getFileList();
        initFileLists();
    }


    private List<String> getFileList() {
        try (Stream<Path> paths = Files.walk(Paths.get(fileLocation))) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString())
                    .collect(toList());
        } catch (IOException e) {
            log().info(e.getMessage());
            return new ArrayList<>();
        }
    }

    private void initFileLists() {
        fileJsDtoList = new ArrayList<>();
        fileJsonDtoList = new ArrayList<>();
        fileNames.forEach(x -> {
            if (x.contains(JSONTYPE)) {
                String[] fileValues = x.replace(JSONTYPE, "").split("_");
                fileJsonDtoList.add(fileJsonValidator(fileValues, x, readLineByLine(fileLocation + File.separator + x)));
            } else if (x.contains(JSTYPE)) {
                String[] fileValues = x.replace(JSTYPE, "").split("_");
                fileJsDtoList.add(fileJsValidator(fileValues, x, readLineByLine(fileLocation + File.separator + x)));
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
