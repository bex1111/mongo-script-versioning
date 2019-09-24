package validator;

import exception.MSVExceptionFactory;
import lombok.experimental.UtilityClass;
import reader.dto.FileJsDto;
import reader.dto.FileJsonDto;

@UtilityClass
public class ValidatorFiles {


    private final static String FOURDIGITREGEX = "^[0-9]{4}$";

    public static FileJsonDto fileJsonValidator(String[] fileValues, String name, String value) {
        if (fileValues.length != 3 || fileValues[1].isBlank()) {
            throw MSVExceptionFactory.wrongJsonFileNameFormat();
        } else if (!fileValues[0].matches(FOURDIGITREGEX)) {
            throw MSVExceptionFactory.wrongVersionFormat();
        }

        return FileJsonDto.builder().value(value)
                .version(fileValues[0]).fileName(name).name(fileValues[1]).collectionName(fileValues[2]).build();
    }

    public static FileJsDto fileJsValidator(String[] fileValues, String name, String value) {
        if (fileValues.length != 2 || fileValues[1].isBlank()) {
            throw MSVExceptionFactory.wrongJsFileNameFormat();
        } else if (!fileValues[0].matches(FOURDIGITREGEX)) {
            throw MSVExceptionFactory.wrongVersionFormat();
        }
        return FileJsDto.builder().value(value)
                .version(fileValues[0]).fileName(name).name(fileValues[1]).build();
    }


}
