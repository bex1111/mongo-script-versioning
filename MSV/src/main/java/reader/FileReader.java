package reader;

import lombok.Data;
import reader.dto.FileBaseDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static reader.ValidatorFiles.fileJsValidator;
import static reader.ValidatorFiles.fileJsonValidator;
import static util.Constans.JSONTYPE;
import static util.Constans.JSTYPE;
import static util.Logger.log;

@Data
public class FileReader {

    private List<String> fileNames;
    private List<FileBaseDto> fileBaseDtos;

    private String fileLocation;


    public FileReader(String fileLocation) {
        this.fileLocation = fileLocation;
        fileNames = getFileList();
        initFileLists();
        fileBaseDtos.sort(Comparator.comparing(FileBaseDto::getVersion));
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
        fileBaseDtos = new ArrayList<>();
        fileNames.forEach(x -> {
            if (x.contains(JSONTYPE)) {
                String[] fileValues = x.replace(JSONTYPE, "").split("_");
                fileBaseDtos.add(fileJsonValidator(fileValues, x));
            } else if (x.contains(JSTYPE)) {
                String[] fileValues = x.replace(JSTYPE, "").split("_");
                fileBaseDtos.add(fileJsValidator(fileValues, x));
            } else {
                log().warn("Not used files: " + x);
            }
        });
    }

}
