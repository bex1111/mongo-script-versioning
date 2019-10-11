package validator.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class FileJsDto extends FileBaseDto {

    @Builder
    public FileJsDto(String fileName, String description, String version) {
        super(fileName, description, version);
    }

}
