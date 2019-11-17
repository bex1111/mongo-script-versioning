package com.github.bex1111.validator;

import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.validator.dto.FileBaseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.bex1111.util.Constans.JSONTYPE;
import static com.github.bex1111.util.Constans.JSTYPE;
import static com.github.bex1111.util.FileHandler.getFileList;
import static com.github.bex1111.util.Logger.log;
import static java.util.stream.Collectors.toList;


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
        mergeTwoList(fileNames, msvRepository.migratedFileNameList()).forEach(x -> {
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

    private List<String> mergeTwoList(List<String> fileNames, List<String> migratedList) {
        migratedList.forEach(x -> {
            if (!fileNames.contains(x)) {
                fileNames.add(x);
            }
        });
        return fileNames;
    }


}
