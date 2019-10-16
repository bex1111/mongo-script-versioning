package validator.dto;

import lombok.Builder;


public class FileJsDto extends FileBaseDto {

    @Builder
    public FileJsDto(String fileName, String description, String version) {
        super(fileName, description, version);
    }

}
