package msv.migrate;

import com.mongodb.DBObject;
import msv.exception.MSVException;
import msv.repository.MSVRepository;
import msv.testutil.TestHelper;
import msv.testutil.TestMSVRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static msv.testutil.FileConst.*;
import static msv.util.Constans.FULLNAME;
import static msv.util.Constans.VERSION;

public class MigrateHandlerTest {

    private TestHelper testHelper;
    private MSVRepository msvRepository;
    private TestMSVRepository testMSVRepository;

    public MigrateHandlerTest() {
        testHelper = new TestHelper();
        msvRepository = new MSVRepository(testHelper.getDb());
        testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    @Test
    public void migrateHandlerTest1() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(),
                Arrays.asList(F0001, F0002), msvRepository);
        List<DBObject> dbObjectList = testMSVRepository.findAll();
        Assertions.assertEquals(2, dbObjectList.size());
        Assertions.assertEquals(F0001.getFileName(), dbObjectList.get(0).get(FULLNAME));
        Assertions.assertEquals(F0001.getVersion(), dbObjectList.get(0).get(VERSION));
        Assertions.assertEquals(F0002.getFileName(), dbObjectList.get(1).get(FULLNAME));
        Assertions.assertEquals(F0002.getVersion(), dbObjectList.get(1).get(VERSION));
    }

    @Test
    public void migrateHandlerTest2() {
        testMSVRepository.clearMsvCollection();
        Assertions.assertThrows(MSVException.class, () -> {
            new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(),
                    Arrays.asList(F0004), msvRepository);
        });
    }

    @Test
    public void migrateHandlerTest3() {
        testMSVRepository.clearMsvCollection();
        Assertions.assertThrows(MSVException.class, () -> {
            new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(),
                    Arrays.asList(F0003), msvRepository);
        });
    }


    @Test
    public void migrateHandlerTest4() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(),
                Arrays.asList(F0001, F0002, FAAAA2), msvRepository);
        List<DBObject> dbObjectList = testMSVRepository.findAll();
        Assertions.assertEquals(3, dbObjectList.size());
        Assertions.assertEquals(F0001.getFileName(), dbObjectList.get(0).get(FULLNAME));
        Assertions.assertEquals(F0001.getVersion(), dbObjectList.get(0).get(VERSION));
        Assertions.assertEquals(F0002.getFileName(), dbObjectList.get(1).get(FULLNAME));
        Assertions.assertEquals(F0002.getVersion(), dbObjectList.get(1).get(VERSION));
        Assertions.assertEquals(FAAAA2.getFileName(), dbObjectList.get(2).get(FULLNAME));
        Assertions.assertEquals(FAAAA2.getVersion(), dbObjectList.get(2).get(VERSION));
    }

}