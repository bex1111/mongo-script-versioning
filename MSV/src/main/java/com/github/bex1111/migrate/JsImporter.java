package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.mongodb.DB;
import com.mongodb.MongoException;

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
