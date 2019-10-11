package migrate;

import com.mongodb.DB;
import com.mongodb.MongoException;
import exception.MSVExceptionFactory;

public class JsImporter extends BaseImporter {

    public JsImporter(DB db) {
        super(db);
    }

    public void executeJsCommand(String fileName, String jsText) {
        try {
            db.eval(jsText);
        } catch (MongoException e) {
            throw MSVExceptionFactory.wrongEvalFail(fileName, e);
        }
    }

}
