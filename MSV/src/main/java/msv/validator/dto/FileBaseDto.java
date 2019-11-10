package msv.validator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileBaseDto {

    private String fileName;
    private String description;
    private String version;

}