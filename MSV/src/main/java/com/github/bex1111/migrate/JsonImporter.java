package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.github.bex1111.repository.MSVRepository;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONParseException;

import java.util.Arrays;
import java.util.stream.Collectors;

public class JsonImporter extends BaseImporter {

    BigJsonArrayImporter bigJsonArrayImporter;

    public JsonImporter(MSVRepository msvRepository) {
        super(msvRepository);
        bigJsonArrayImporter = new BigJsonArrayImporter(msvRepository);
    }

    public void importJson(String fileName, String[] jsonText, String collectionName) {
        try {

            if (!bigJsonArrayImporter.isJsonArray(jsonText[0], fileName)) {
                Object dbObject = JSON.parse(Arrays.stream(jsonText).collect(Collectors.joining()));
                msvRepository.insertDbObject(collectionName, (DBObject) dbObject);
            } else if (bigJsonArrayImporter.isJsonArray(jsonText[0], fileName)) {
                bigJsonArrayImporter.processJsonArray(collectionName, jsonText);
            }
        } catch (JSONParseException e) {
            throw MSVExceptionFactory.jsonParseFail(fileName, e);
        }
    }

}
