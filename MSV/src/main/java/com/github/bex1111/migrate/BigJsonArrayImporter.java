package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.github.bex1111.repository.MSVRepository;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.codehaus.plexus.util.StringUtils;

public class BigJsonArrayImporter extends BaseImporter {

    public BigJsonArrayImporter(MSVRepository msvRepository) {
        super(msvRepository);
    }

    private final static String JOININGCHARACHTER = ",";

    public void processJsonArray(String collectionName, String[] jsonText) {
        deleteFirstAndLastArrayChar(jsonText);
        String objectString = "";
        for (int i = 0; i < jsonText.length; i++) {
            objectString = objectString + JOININGCHARACHTER + jsonText[i];
            if (StringUtils.countMatches(objectString, "{") == StringUtils.countMatches(objectString, "}")) {
                msvRepository.insertDbObject(collectionName, (DBObject) ((BasicDBList) JSON.parse("[" + objectString.replaceFirst(",", "") + "]")).get(0));
                objectString = "";
            }
        }
    }

    private void deleteFirstAndLastArrayChar(String[] jsonText) {
        jsonText[0] = jsonText[0].replaceFirst("\\[", "");
        jsonText[jsonText.length - 1] = replaceLast(jsonText[jsonText.length - 1], "]", "");
    }

    private String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    public boolean isJsonArray(String jsonText, String fileName) {
        for (int i = 0; i < jsonText.length(); i++) {
            if (jsonText.charAt(i) == '[') {
                return true;
            } else if (jsonText.charAt(i) == '{') {
                return false;
            }
        }
        throw MSVExceptionFactory.jsonParseFail(fileName);
    }
}
