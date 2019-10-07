package validator.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class FileJsonDto extends FileBaseDto {


    private String collectionName;


    @Builder
    public FileJsonDto(String fileName, String name, String version, String collectionName) {
        super(fileName, name, version);
        this.collectionName = collectionName;
    }
}
