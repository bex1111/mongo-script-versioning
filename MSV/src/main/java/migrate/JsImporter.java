package migrate;

import com.mongodb.DB;
import com.mongodb.MongoException;

public class JsImporter extends BaseImporter {

    public JsImporter(DB db) {
        super(db);
    }

    public void executeJsCommand(String jsText) {
        try {
            db.eval(jsText);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

}
