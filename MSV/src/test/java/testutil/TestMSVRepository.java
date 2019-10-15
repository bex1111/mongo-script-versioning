package testutil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;

import java.util.List;

import static util.Constans.*;

@AllArgsConstructor
public class TestMSVRepository {

    private DB db;
    private static final String MSVCOLLECTIONNAME = "msv_migrate";

    public void clearMsvCollection() {
        db.getCollection(MSVCOLLECTIONNAME).drop();
    }


    public void dropCollection(String name) {
        db.getCollection(name).drop();
    }

    public List<DBObject> findAll() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find();
        return cursor.toArray();
    }

    public List<DBObject> findAllInCollection(String name) {
        DBCursor cursor = db.getCollection(name).find();
        return cursor.toArray();
    }

    public void fillDummyVersion() {
        clearMsvCollection();
        db.getCollection(MSVCOLLECTIONNAME).insert(new BasicDBObject(VERSION, "0001"), new BasicDBObject(VERSION, "0002"));
    }

    public void fillDummyObject() {
        BasicDBObject newFile = new BasicDBObject();
        newFile.put(COLLECTIONNAME, "asd");
        newFile.put(TYPE, "Json");
        newFile.put(VERSION, "asd");
        newFile.put(DESCRIPTION, "asd");
        newFile.put(FULLNAME, "0002_test_test.json");
        newFile.put(CHECKSUM, "asd");
        newFile.put(INSTALLEDBY, "asd");
        newFile.put(DATE, "asd");
        BasicDBObject newFile2 = new BasicDBObject();
        newFile2.put(TYPE, "Js");
        newFile2.put(VERSION, "dummy");
        newFile2.put(DESCRIPTION, "dummy");
        newFile2.put(FULLNAME, "0001_test.js");
        newFile2.put(CHECKSUM, "dummy");
        newFile2.put(INSTALLEDBY, "dummy");
        newFile2.put(DATE, "dummy");
        db.getCollection(MSVCOLLECTIONNAME).insert(newFile);
        db.getCollection(MSVCOLLECTIONNAME).insert(newFile2);
    }


}
