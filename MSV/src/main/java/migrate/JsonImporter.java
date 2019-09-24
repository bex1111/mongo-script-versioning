package migrate;

import com.mongodb.*;
import com.mongodb.util.JSON;

public class JsonImporter extends BaseImporter {

    public JsonImporter(DB db) {
        super(db);
    }

    public void importJson(String jsonText, String collctionName) {
        try {

            DBCollection collection = db.getCollection(collctionName);

            Object dbObject = JSON.parse(jsonText);
            if (dbObject instanceof BasicDBObject) {
                collection.insert((DBObject) dbObject);
            } else if (dbObject instanceof BasicDBList) {
                ((BasicDBList) dbObject).forEach(item -> collection.insert((DBObject) item));
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

}
