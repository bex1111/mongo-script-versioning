package testutil;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;

import java.util.List;

import static util.Constans.VERSION;

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


}
