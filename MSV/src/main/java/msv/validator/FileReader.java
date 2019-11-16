package msv.validator;

import lombok.Getter;
import msv.repository.MSVRepository;
import msv.validator.dto.FileBaseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static msv.util.Constans.JSONTYPE;
import static msv.util.Constans.JSTYPE;
import static msv.util.FileHandler.getFileList;
import static msv.util.Logger.log;


public class FileReader {

    @Getter
    private List<FileBaseDto> newFileBaseDtos;

    private final MSVRepository msvRepository;
    private final ValidatorFiles validatorFiles;
    private final String fileLocation;


    public FileReader(String fileLocation, MSVRepository msvRepository) {
        this.fileLocation = fileLocation;
        this.msvRepository = msvRepository;
        this.validatorFiles = new ValidatorFiles(msvRepository);
        proccessing();
    }

    private void proccessing() {
        List<String> fileNames = getFileList(fileLocation);
        List<FileBaseDto> fileBaseDtos = initFileLists(fileNames);
        validatorFiles.listUniq(fileBaseDtos.stream().map(x -> x.getVersion()).collect(toList()));
        generateNewFileBaseDtos(fileBaseDtos);
    }

    private void generateNewFileBaseDtos(List<FileBaseDto> fileBaseDtos) {
        newFileBaseDtos = new ArrayList<>();
        Optional<String> maxVersion = msvRepository.getMaxVersion();
        if (maxVersion.isPresent()) {
            fileBaseDtos.stream().filter(x -> 0 < x.getVersion().compareTo(maxVersion.get())).forEach(x -> newFileBaseDtos.add(x));
        } else {
            newFileBaseDtos.addAll(fileBaseDtos);
        }

    }

    private List<FileBaseDto> initFileLists(List<String> fileNames) {
        List<FileBaseDto> fileBaseDtos = new ArrayList<>();
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
        return fileBaseDtos;
    }


}
