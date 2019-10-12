package migrate;

import com.mongodb.DBObject;
import exception.MSVException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testutil.TestHelper;
import testutil.TestMSVRepository;

import java.net.UnknownHostException;
import java.util.List;

public class JsonImporterTest {

    private TestHelper testHelper;
    private TestMSVRepository testMSVRepository;
    private JsonImporter jsonImporter;
    private static String TESTCOLLECTIONNAME = "test";

    public JsonImporterTest() {
        try {
            testHelper = new TestHelper();
        } catch (UnknownHostException e) {
            Assertions.fail("No mongo");
        }
        testMSVRepository = new TestMSVRepository(testHelper.getDb());
        jsonImporter = new JsonImporter(testHelper.getDb());
    }

    @Test
    public void jsonImporterTest1() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        jsonImporter.importJson("dummy", "[{\n" +
                "  \"asd\":\"asd\"\n" +
                "},{\n" +
                "  \"test\":\"test\"\n" +
                "}\n" +
                "  ]", TESTCOLLECTIONNAME);
        List<DBObject> dbObjectList = testMSVRepository.findAllInCollection(TESTCOLLECTIONNAME);
        Assertions.assertEquals(dbObjectList.size(), 2);
        Assertions.assertEquals(dbObjectList.get(0).get("asd"), "asd");
        Assertions.assertEquals(dbObjectList.get(1).get("test"), "test");
    }


    @Test
    public void jsonImporterTest2() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        jsonImporter.importJson("dummy", "{\n" +
                "  \"asd\":\"asd\"\n" +
                "}", TESTCOLLECTIONNAME);
        List<DBObject> dbObjectList = testMSVRepository.findAllInCollection(TESTCOLLECTIONNAME);
        Assertions.assertEquals(dbObjectList.size(), 1);
        Assertions.assertEquals(dbObjectList.get(0).get("asd"), "asd");
    }

    @Test
    public void jsonImporterTest3() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        Assertions.assertThrows(MSVException.class, () -> {
            jsonImporter.importJson("dummy", "{\n" +
                    "  \"asd\" \"asd\"\n" +
                    "}", TESTCOLLECTIONNAME);
        });
    }

    @Test
    public void jsonImporterTest4() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        Assertions.assertThrows(MSVException.class, () -> {
            jsonImporter.importJson("dummy", "[{\n" +
                    "  \"asd\":\"asd\"\n" +
                    "}  {\n" +
                    "  \"test\":\"test\"\n" +
                    "}\n" +
                    "  ]", TESTCOLLECTIONNAME);
        });
    }

    @Test
    public void jsonImporterTest5() {
        testMSVRepository.dropCollection(TESTCOLLECTIONNAME);
        jsonImporter.importJson("dummy", "[{\n" +
                "  \"asd\":\"asd\"\n" +
                "},{\n" +
                "  \"test\":\"test\"\n" +
                "}\n" + ",{\n" +
                "  \"test\":\"test\"\n" +
                "}\n" +
                "  ]", TESTCOLLECTIONNAME);
        List<DBObject> dbObjectList = testMSVRepository.findAllInCollection(TESTCOLLECTIONNAME);
        Assertions.assertEquals(dbObjectList.size(), 3);
        Assertions.assertEquals(dbObjectList.get(0).get("asd"), "asd");
        Assertions.assertEquals(dbObjectList.get(1).get("test"), "test");
        Assertions.assertEquals(dbObjectList.get(2).get("test"), "test");
    }


}
