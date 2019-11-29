package com.github.bex1111.migrate;

import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.validator.dto.FileBaseDto;
import com.github.bex1111.validator.dto.FileJsDto;
import com.github.bex1111.validator.dto.FileJsonDto;
import com.github.bex1111.validator.dto.FileType;
import com.mongodb.BasicDBObject;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.bex1111.util.Constans.*;
import static com.github.bex1111.util.FileHandler.readLineByLine;
import static com.github.bex1111.util.Hash.generateSha512;
import static com.github.bex1111.util.Logger.log;

public class MigrateHandler {

    private String fileLocation;
    private JsImporter jsImporter;
    private JsonImporter jsonImporter;
    private MSVRepository msvRepository;


    public MigrateHandler(String fileLocation, List<FileBaseDto> fileBaseDtoList, MSVRepository msvRepository) {
        this.fileLocation = fileLocation;
        this.msvRepository = msvRepository;
        this.jsonImporter = new JsonImporter(msvRepository);
        this.jsImporter = new JsImporter(msvRepository);
        startMigrate(fileBaseDtoList);
    }

    private void startMigrate(List<FileBaseDto> fileBaseDtoList) {
        fileBaseDtoList.forEach(item -> {
            if (item instanceof FileJsDto) {
                jsImporter.executeJsCommand(item.getFileName(), readLineByLine(fileLocation, item.getFileName()));
                msvRepository.insertNewFileToMsv(generateJsBasicObject((FileJsDto) item));
            } else {
                jsonImporter.importJson(item.getFileName(), readLineByLine(fileLocation, item.getFileName()).split(","), ((FileJsonDto) item).getCollectionName());
                msvRepository.insertNewFileToMsv(generateJsonBasicObject((FileJsonDto) item));
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
