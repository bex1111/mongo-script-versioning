package reader.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class FileJsonDto extends FileBaseDto {


    private String collectionName;


    @Builder
    public FileJsonDto(String fileName, String name, String version, String value, String collectionName) {
        super(fileName, name, version, value);
        this.collectionName = collectionName;
    }
}
