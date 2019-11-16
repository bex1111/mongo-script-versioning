package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVException;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JsImporterTest {

    private TestHelper testHelper;
    private TestMSVRepository testMSVRepository;
    private JsImporter jsImporter;
    private static String TESTCOLLECTIONNAME = "test";

    public JsImporterTest() {
        testHelper = new TestHelper();
        testMSVRepository = new TestMSVRepository(testHelper.getDb());
        jsImporter = new JsImporter(testHelper.getDb());
    }

    @Test
    public void jsImporterTest1() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        jsImporter.executeJsCommand("dummy", "db.getCollection('test').save({\"Sandor\":\"test\"});\n" +
                "db.getCollection('test').save({\"Sandor\":\"test\"});");
        List<DBObject> dbObjectList = testMSVRepository.findAllInCollection(TESTCOLLECTIONNAME);
        Assertions.assertEquals(dbObjectList.size(), 2);
        Assertions.assertEquals(dbObjectList.get(0).get("Sandor"), "test");
        Assertions.assertEquals(dbObjectList.get(1).get("Sandor"), "test");
    }

    @Test
    public void jsImporterTest2() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        Assertions.assertThrows(MSVException.class, () -> {
            jsImporter.executeJsCommand("dummy", "db.getCollection('test').save({\"Sandor\":\"test\"}) " +
                    "db.getCollection('test').save({\"Sandor\":\"test\"});");
        });
    }

    @Test
    public void jsImporterTest3() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        jsImporter.executeJsCommand("dummy", "db.getCollection('test').save({\"Sandor\":\"test\"});\n" +
                "db.getCollection('test').save({\"Sandor\":\"test\"});\n" +
                "db.getCollection('test').save({\"Sandor\":\"asd\"});\n" +
                "db.getCollection('test').save({\"Sandor\":\"asd\"});");
        List<DBObject> dbObjectList = testMSVRepository.findAllInCollection(TESTCOLLECTIONNAME);
        Assertions.assertEquals(dbObjectList.size(), 4);
        Assertions.assertEquals(dbObjectList.get(0).get("Sandor"), "test");
        Assertions.assertEquals(dbObjectList.get(1).get("Sandor"), "test");
        Assertions.assertEquals(dbObjectList.get(2).get("Sandor"), "asd");
        Assertions.assertEquals(dbObjectList.get(3).get("Sandor"), "asd");
    }

}
