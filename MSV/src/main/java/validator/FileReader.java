package validator;

import lombok.Data;
import repository.MSVRepository;
import validator.dto.FileBaseDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static util.Constans.JSONTYPE;
import static util.Constans.JSTYPE;
import static util.FileHandler.getFileList;
import static util.Logger.log;

@Data
public class FileReader {

    private List<String> fileNames;
    private List<FileBaseDto> fileBaseDtos;
    private List<FileBaseDto> newFileBaseDtos;
    private MSVRepository msvRepository;
    private ValidatorFiles validatorFiles;

    private String fileLocation;
    private String[] fileValues;


    public FileReader(String fileLocation, MSVRepository msvRepository) {
        this.fileLocation = fileLocation;
        this.msvRepository = msvRepository;
        validatorFiles = new ValidatorFiles(msvRepository);
        fileNames = getFileList(fileLocation);
        initFileLists();
        validatorFiles.listUniq(fileBaseDtos.stream().map(x -> x.getVersion()).collect(toList()));
        fileBaseDtos.sort(Comparator.comparing(FileBaseDto::getVersion));
        generateNewFileBaseDtos();
    }

    private void generateNewFileBaseDtos() {
        newFileBaseDtos = new ArrayList<>();
        Optional<String> maxVersion = msvRepository.getMaxVersion();
        if (maxVersion.isPresent()) {
            fileBaseDtos.stream().filter(x -> 1 == x.getVersion().compareTo(maxVersion.get())).forEach(x -> newFileBaseDtos.add(x));
        } else {
            newFileBaseDtos.addAll(fileBaseDtos);
        }

    }

    private void initFileLists() {
        fileBaseDtos = new ArrayList<>();
        fileNames.forEach(x -> {
            if (x.contains(JSONTYPE)) {
                String[] fileValues = x.replace(JSONTYPE, "").split("_");
                validatorFiles.validateFileText(fileLocation, x);
                fileBaseDtos.add(validatorFiles.fileJsonValidator(fileValues, x));
            } else if (x.contains(JSTYPE)) {
                String[] fileValues = x.replace(JSTYPE, "").split("_");
                validatorFiles.validateFileText(fileLocation, x);
                fileBaseDtos.add(validatorFiles.fileJsValidator(fileValues, x));
            } else {
                log().warn("Not used files: " + x);
            }
        });
    }


}
