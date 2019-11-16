package com.github.bex1111.validator;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.util.Hash;
import com.github.bex1111.validator.dto.FileJsDto;
import com.github.bex1111.validator.dto.FileJsonDto;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.github.bex1111.util.FileHandler.readLineByLine;


@RequiredArgsConstructor
public class ValidatorFiles {

    private final MSVRepository msvRepository;
    private final String CHARACHTERORNUMBERREQEX = "^[a-zA-Z0-9]+$";

    public FileJsonDto fileJsonValidator(String[] fileValues, String name) {
        if (fileValues.length != 3 || name.contains(" ")) {
            throw MSVExceptionFactory.wrongJsonFileNameFormat();
        }
        versionValidator(fileValues[0]);
        return FileJsonDto.builder()
                .version(fileValues[0]).fileName(name).description(fileValues[1]).collectionName(fileValues[2]).build();
    }

    private void versionValidator(String version) {
        if (!version.matches(CHARACHTERORNUMBERREQEX)) {
            throw MSVExceptionFactory.wrongVersionFormat();
        }
    }

    public FileJsDto fileJsValidator(String[] fileValues, String name) {
        if (fileValues.length != 2 || name.contains(" ")) {
            throw MSVExceptionFactory.wrongJsFileNameFormat();
        }
        versionValidator(fileValues[0]);
        return FileJsDto.builder()
                .version(fileValues[0]).fileName(name).description(fileValues[1]).build();
    }

    public void listUniq(List<String> fileVersion) {
        if (new HashSet<String>(fileVersion).size() != fileVersion.size()) {
            throw MSVExceptionFactory.notUniqVersion();
        }
    }


    public void validateFileText(String fileLocation, String fileName) {
        Optional<String> checkSum = msvRepository.findCheckSum(fileName);
        if (checkSum.isPresent() && !Hash.generateSha512(readLineByLine(fileLocation, fileName)).equals(checkSum.get())) {
            throw MSVExceptionFactory.hashNotEqual(fileName);
        }

    }
}
