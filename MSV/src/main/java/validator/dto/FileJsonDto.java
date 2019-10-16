package validator.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class FileJsonDto extends FileBaseDto {


    private String collectionName;

    @Builder
    public FileJsonDto(String fileName, String description, String version, String collectionName) {
        super(fileName, description, version);
        this.collectionName = collectionName;
    }


}
