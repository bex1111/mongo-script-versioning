package reader.dto;

import lombok.Builder;
import lombok.Data;


@Data
public class FileJsDto extends FileBaseDto {

    @Builder
    public FileJsDto(String fileName, String name, String version) {
        super(fileName, name, version);
    }
}
