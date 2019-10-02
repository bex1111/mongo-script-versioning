package repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;
import validator.dto.FileBaseDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static util.Constans.*;
import static util.FileLoader.readLineByLine;
import static util.Hash.generateSha512;

@AllArgsConstructor
public class MSVRepository {

    private DB db;
    private String fileLocation;
    private static final String MSVCOLLECTIONNAME = "msv_migrate";

    public List<DBObject> findAll() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find();
        return cursor.toArray();
    }

    public Optional<String> findCheckSum(String fileName) {
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(FULLNAME, fileName);
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find(whereQuery);
        return Optional.ofNullable(cursor.curr().get(CHECKSUM).toString());
    }

//    public Optional<String> getMaxVersion() {
//       //TODO native query
//    }

    public void insertNewFile(FileBaseDto fileBaseDto) {
        BasicDBObject newFile = new BasicDBObject();
        newFile.put(VERSION, fileBaseDto.getVersion());
        newFile.put(DESCRIPTION, fileBaseDto.getDescription());
        newFile.put(FULLNAME, fileBaseDto.getFileName());
        newFile.put(CHECKSUM, generateSha512(readLineByLine(fileLocation, fileBaseDto.getFileName())));
        newFile.put(INSTALLEDBY, System.getProperty("user.name"));
        newFile.put(DATE, LocalDateTime.now().toString());
        db.getCollection(MSVCOLLECTIONNAME).insert(newFile);
    }


}
