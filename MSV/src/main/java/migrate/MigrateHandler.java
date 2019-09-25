package migrate;

import com.mongodb.DB;
import reader.dto.FileBaseDto;
import reader.dto.FileJsDto;
import reader.dto.FileJsonDto;
import repository.MSVRepository;

import java.util.List;

import static util.FileLoader.readLineByLine;

public class MigrateHandler {

    private String fileLocation;
    private DB db;
    private JsImporter jsImporter;
    private JsonImporter jsonImporter;
    private MSVRepository msvRepository;


    public MigrateHandler(String fileLocation, DB db, List<FileBaseDto> fileBaseDtoList, MSVRepository msvRepository) {
        this.fileLocation = fileLocation;
        this.db = db;
        this.jsonImporter = new JsonImporter(db);
        this.msvRepository = msvRepository;
        this.jsImporter = new JsImporter(db);
        startMigrate(fileBaseDtoList);
    }

    private void startMigrate(List<FileBaseDto> fileBaseDtoList) {
        fileBaseDtoList.forEach(item -> {
            if (item instanceof FileJsDto) {
                jsImporter.executeJsCommand(item.getFileName(), readLineByLine(fileLocation, item.getFileName()));
            } else {
                jsonImporter.importJson(item.getFileName(), readLineByLine(fileLocation, item.getFileName()), ((FileJsonDto) item).getCollectionName());
            }
            msvRepository.insertNewFile(item);
        });
    }


}
