package com.github.bex1111.migrate;

import com.github.bex1111.exception.MSVException;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.bex1111.testutil.FileConst.*;
import static com.github.bex1111.util.Constans.FULLNAME;
import static com.github.bex1111.util.Constans.VERSION;

public class MigrateHandlerTest {

    private TestHelper testHelper;

    private TestMSVRepository testMSVRepository;

    public MigrateHandlerTest() {
        testHelper = new TestHelper();
        testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    @Test
    public void migrateHandlerTest1() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(),
                Arrays.asList(F0001, F0002), testHelper.getMsvRepository());
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
            new MigrateHandler(testHelper.getFileLocation(),
                    Arrays.asList(F0004), testHelper.getMsvRepository());
        });
    }

    @Test
    public void migrateHandlerTest3() {
        testMSVRepository.clearMsvCollection();
        Assertions.assertThrows(MSVException.class, () -> {
            new MigrateHandler(testHelper.getFileLocation(),
                    Arrays.asList(F0003), testHelper.getMsvRepository());
        });
    }


    @Test
    public void migrateHandlerTest4() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(),
                Arrays.asList(F0001, F0002, FAAAA2), testHelper.getMsvRepository());
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
