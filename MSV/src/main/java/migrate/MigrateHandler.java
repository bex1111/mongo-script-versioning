package migrate;

import com.mongodb.DB;
import reader.dto.FileBaseDto;
import reader.dto.FileJsDto;
import reader.dto.FileJsonDto;

import java.io.File;
import java.util.List;

import static util.FileLoader.readLineByLine;

public class MigrateHandler {

    private String fileLocation;
    private DB db;
    private JsImporter jsImporter;
    private JsonImporter jsonImporter;

    public MigrateHandler(String fileLocation, DB db, List<FileBaseDto> fileBaseDtoList) {
        this.fileLocation = fileLocation;
        this.db = db;
        this.jsonImporter = new JsonImporter(db);
        this.jsImporter = new JsImporter(db);
        startMigrate(fileBaseDtoList);
    }

    private void startMigrate(List<FileBaseDto> fileBaseDtoList) {
        fileBaseDtoList.forEach(item -> {
            if (item instanceof FileJsDto) {
                jsImporter.executeJsCommand(readLineByLine(fileLocation + File.separator + item.getFileName()));
            } else {
                jsonImporter.importJson(readLineByLine(fileLocation + File.separator + item.getFileName()), ((FileJsonDto) item).getCollectionName());
            }
        });
    }


}
