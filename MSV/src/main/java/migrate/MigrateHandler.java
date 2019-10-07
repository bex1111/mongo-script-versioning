package migrate;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import repository.MSVRepository;
import validator.dto.FileBaseDto;
import validator.dto.FileJsDto;
import validator.dto.FileJsonDto;
import validator.dto.FileType;

import java.time.LocalDateTime;
import java.util.List;

import static util.Constans.*;
import static util.FileHandler.readLineByLine;
import static util.Hash.generateSha512;
import static util.Logger.log;

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
                msvRepository.insertNewFile(generateJsBasicObject((FileJsDto) item));
            } else {
                jsonImporter.importJson(item.getFileName(), readLineByLine(fileLocation, item.getFileName()), ((FileJsonDto) item).getCollectionName());
                msvRepository.insertNewFile(generateJsonBasicObject((FileJsonDto) item));
            }
            log().info("Migrate file: " + item.getFileName());

        });
    }

    private BasicDBObject generateJsonBasicObject(FileJsonDto fileBaseDto) {
        BasicDBObject newFile = new BasicDBObject();
        generateBasicObject(newFile, fileBaseDto);
        newFile.put(COLLECTIONNAME, fileBaseDto.getCollectionName());
        newFile.put(TYPE, FileType.JSON.getType());
        return newFile;
    }

    private void generateBasicObject(BasicDBObject newFile, FileBaseDto fileBaseDto) {
        newFile.put(VERSION, fileBaseDto.getVersion());
        newFile.put(DESCRIPTION, fileBaseDto.getDescription());
        newFile.put(FULLNAME, fileBaseDto.getFileName());
        newFile.put(CHECKSUM, generateSha512(readLineByLine(fileLocation, fileBaseDto.getFileName())));
        newFile.put(INSTALLEDBY, System.getProperty("user.name"));
        newFile.put(DATE, LocalDateTime.now().toString());
    }

    private BasicDBObject generateJsBasicObject(FileJsDto fileBaseDto) {
        BasicDBObject newFile = new BasicDBObject();
        generateBasicObject(newFile, fileBaseDto);
        newFile.put(TYPE, FileType.JS.getType());
        return newFile;
    }


}
