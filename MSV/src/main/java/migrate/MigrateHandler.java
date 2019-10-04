package migrate;

import com.mongodb.DB;
import repository.MSVRepository;
import validator.dto.FileBaseDto;
import validator.dto.FileJsDto;
import validator.dto.FileJsonDto;

import java.util.List;

import static util.FileHandler.readLineByLine;

public class MigrateHandler {

    private String fileLocation;

    private JsImporter jsImporter;
    private JsonImporter jsonImporter;
    private MSVRepository msvRepository;


    public MigrateHandler(String fileLocation, DB db, List<FileBaseDto> fileBaseDtoList, MSVRepository msvRepository) {
        this.fileLocation = fileLocation;
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
            msvRepository.insertNewFile(item, fileLocation);
        });
    }


}
