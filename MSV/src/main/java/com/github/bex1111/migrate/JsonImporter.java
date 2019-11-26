package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JsonImporter extends BaseImporter {

    BigJsonArrayImporter bigJsonArrayImporter;

    public JsonImporter(DB db) {
        super(db);
        bigJsonArrayImporter = new BigJsonArrayImporter();
    }

    public void importJson(String fileName, String[] jsonText, String collctionName) {
        try {
            DBCollection collection = db.getCollection(collctionName);

            if (!bigJsonArrayImporter.isJsonArray(jsonText[0], fileName)) {
                Object dbObject = JSON.parse(Arrays.stream(jsonText).collect(Collectors.joining()));
                collection.insert((DBObject) dbObject);
            } else if (bigJsonArrayImporter.isJsonArray(jsonText[0], fileName)) {
                bigJsonArrayImporter.processJsonArray(collection, jsonText);
            }
        } catch (JSONParseException e) {
            throw MSVExceptionFactory.jsonParseFail(fileName, e);
        }
    }

}
