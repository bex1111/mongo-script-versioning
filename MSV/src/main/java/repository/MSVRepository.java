package repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import lombok.AllArgsConstructor;
import reader.dto.FileBaseDto;

import java.time.LocalDateTime;

import static util.FileLoader.readLineByLine;
import static util.Hash.generateSha512;

@AllArgsConstructor
public class MSVRepository {

    private DB db;
    private String fileLocation;
    private static final String MSVCOLLECTIONNAME = "msv_migrate";

    public void selectAllRecordsFromACollection() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public void insertNewFile(FileBaseDto fileBaseDto) {
        BasicDBObject newFile = new BasicDBObject();
        newFile.put("version", fileBaseDto.getVersion());
        newFile.put("description", fileBaseDto.getVersion());
        newFile.put("full_name", fileBaseDto.getFileName());
        newFile.put("checksum", generateSha512(readLineByLine(fileLocation, fileBaseDto.getFileName())));
        newFile.put("installed_by", System.getProperty("user.name"));
        newFile.put("date", LocalDateTime.now());
        db.getCollection(MSVCOLLECTIONNAME).insert(newFile);
    }
//    version	A fájl névben található verzió
//    description	A fájl névben található rövid név
//    type	Típus (js vagy json)
//    full_name	A fájl teljes neve
//    checksum	Az állományból készült kivonat
//    installed_by	A telepítő neve
//    date	A befuttatás dátuma
//    status	A befuttatás státusza (peding, success, fail)

}
