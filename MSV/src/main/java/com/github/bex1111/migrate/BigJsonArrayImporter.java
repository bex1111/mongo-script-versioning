package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVExceptionFactory;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.codehaus.plexus.util.StringUtils;

public class BigJsonArrayImporter {

    private int avObjectSize;
    private final static String JOININGCHARACHTER = ",";

    public BigJsonArrayImporter() {
        this.avObjectSize = 10;
    }


    public void processJsonArray(DBCollection collection, String[] jsonText) {
        deleteFirstAndLastArrayChar(jsonText);
        String objectString = "";
        for (int i = 0; i < jsonText.length; i++) {
            objectString = objectString + JOININGCHARACHTER + jsonText[i];
            if (StringUtils.countMatches(objectString, "{") == StringUtils.countMatches(objectString, "}")) {
                collection.insert((DBObject) ((BasicDBList) JSON.parse("[" + objectString.replaceFirst(",", "") + "]")).get(0));
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
