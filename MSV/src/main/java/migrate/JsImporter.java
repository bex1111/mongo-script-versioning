package migrate;

import com.mongodb.DB;
import com.mongodb.MongoException;
import exception.MSVExceptionFactory;

import static util.Logger.log;

public class JsImporter extends BaseImporter {

    public JsImporter(DB db) {
        super(db);
    }

    public void executeJsCommand(String fileName, String jsText) {
        try {
            db.eval(jsText);
        } catch (MongoException e) {
            log().error(e.getMessage());
            throw MSVExceptionFactory.wrongEvalFail(fileName);
        }
    }

}
