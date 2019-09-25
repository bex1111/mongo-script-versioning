package migrate;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;
import exception.MSVExceptionFactory;

import static util.Logger.log;

public class JsonImporter extends BaseImporter {

    public JsonImporter(DB db) {
        super(db);
    }

    public void importJson(String fileName, String jsonText, String collctionName) {
        try {
            DBCollection collection = db.getCollection(collctionName);
            Object dbObject = JSON.parse(jsonText);
            if (dbObject instanceof BasicDBObject) {
                collection.insert((DBObject) dbObject);
            } else if (dbObject instanceof BasicDBList) {
                ((BasicDBList) dbObject).forEach(item -> collection.insert((DBObject) item));
            }
        } catch (JSONParseException e) {
            log().error(e.getMessage());
            throw MSVExceptionFactory.jsonParseFail(fileName);
        } catch (MongoException e) {
            log().error(e.getMessage());
            throw MSVExceptionFactory.jsonInsertFail(fileName);
        }
    }

}
