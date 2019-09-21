package migrate;

import dto.FileJsDto;
import dto.FileJsonDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static util.Logger.log;


public class FileReader {

    List<String> filePathNames;
    List<FileJsDto> fileJsDtoList;
    List<FileJsonDto> fileJsonDtoList;

    public FileReader(String path) {
        filePathNames = getFileList(path);
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

    private 
}
