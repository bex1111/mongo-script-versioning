package validator;

import com.mongodb.DBObject;
import exception.MSVExceptionFactory;
import validator.dto.FileJsDto;
import validator.dto.FileJsonDto;

import java.util.HashSet;
import java.util.List;


public class ValidatorFiles {

    public FileJsonDto fileJsonValidator(String[] fileValues, String name) {
        if (fileValues.length != 3 || fileValues[1].isBlank()) {
            throw MSVExceptionFactory.wrongJsonFileNameFormat();
        }

        return FileJsonDto.builder()
                .version(fileValues[0]).fileName(name).name(fileValues[1]).collectionName(fileValues[2]).build();
    }

    public FileJsDto fileJsValidator(String[] fileValues, String name) {
        if (fileValues.length != 2 || fileValues[1].isBlank()) {
            throw MSVExceptionFactory.wrongJsFileNameFormat();
        }
        return FileJsDto.builder()
                .version(fileValues[0]).fileName(name).name(fileValues[1]).build();
    }

    public void listUniq(List<String> fileVersion) {
        if (new HashSet<String>(fileVersion).size() != fileVersion.size()) {
            throw MSVExceptionFactory.notUniqVersion();
        }
    }

    public void textNotChange(DBObject dbObject,String text)
    {

    }
}
