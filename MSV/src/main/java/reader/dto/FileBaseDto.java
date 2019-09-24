package reader.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileBaseDto {

    private String fileName;
    private String name;
    private String version;

}
