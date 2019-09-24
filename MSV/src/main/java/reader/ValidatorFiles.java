package reader;

import exception.MSVExceptionFactory;
import lombok.experimental.UtilityClass;
import reader.dto.FileJsDto;
import reader.dto.FileJsonDto;

@UtilityClass
public class ValidatorFiles {

    public static FileJsonDto fileJsonValidator(String[] fileValues, String name) {
        if (fileValues.length != 3 || fileValues[1].isBlank()) {
            throw MSVExceptionFactory.wrongJsonFileNameFormat();
        }

        return FileJsonDto.builder()
                .version(fileValues[0]).fileName(name).name(fileValues[1]).collectionName(fileValues[2]).build();
    }

    public static FileJsDto fileJsValidator(String[] fileValues, String name) {
        if (fileValues.length != 2 || fileValues[1].isBlank()) {
            throw MSVExceptionFactory.wrongJsFileNameFormat();
        }
        return FileJsDto.builder()
                .version(fileValues[0]).fileName(name).name(fileValues[1]).build();
    }


}
