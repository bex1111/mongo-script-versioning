package testutil;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestMSVRepository {

    private DB db;
    private static final String MSVCOLLECTIONNAME = "msv_migrate";

    public void clearAll() {
        db.getCollection(MSVCOLLECTIONNAME).drop();
    }

    public List<DBObject> findAll() {
        DBCursor cursor = db.getCollection(MSVCOLLECTIONNAME).find();
        return cursor.toArray();
    }


}
