package validator;

import exception.MSVExceptionFactory;
import lombok.experimental.UtilityClass;
import reader.dto.FileJsonDto;

@UtilityClass
public class ValidatorFiles {

    public static FileJsonDto fileJsonValidator(String[] fileValues, String name, String value) {
        if (fileValues.length != 3) {
            throw MSVExceptionFactory.wrongFileNameFormat();
        } else if (fileValues[0].matches("^[0-9]{4}$")) {
            throw MSVExceptionFactory.wrongVersionFormat();
        } else if (fileValues[1].isBlank()) {

        }

        return FileJsonDto.builder().value(value)
                .version(fileValues[0]).fileName(name).name(fileValues[1]).collectionName(fileValues[2]).build();
    }


}
