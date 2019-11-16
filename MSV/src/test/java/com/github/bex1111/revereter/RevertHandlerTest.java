package com.github.bex1111.revereter;

import com.github.bex1111.exception.MSVException;
import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.bex1111.util.Constans.VERSION;

public class RevertHandlerTest {
    private TestHelper testHelper;
    private MSVRepository msvRepository;
    private TestMSVRepository testMSVRepository;

    public RevertHandlerTest() {
        testHelper = new TestHelper();
        msvRepository = new MSVRepository(testHelper.getDb());
        testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    //TODO version, no version, empty coll,non exist version,

    @Test
    public void revertHandlerTestVersion() {
        testMSVRepository.fillDummyVersion();
        new RevertHandler(msvRepository, "0001");
        List<DBObject> dbObjectList = testMSVRepository.findAll();
        Assertions.assertEquals(1, dbObjectList.size());
        Assertions.assertEquals("0001", dbObjectList.get(0).get(VERSION));
    }

    @Test
    public void revertHandlerTestNoVersion() {
        testMSVRepository.fillDummyVersion();
        new RevertHandler(msvRepository, null);
        List<DBObject> dbObjectList = testMSVRepository.findAll();
        Assertions.assertEquals(0, dbObjectList.size());
    }

    @Test
    public void revertHandlerTestEmptyColl() {
        testMSVRepository.clearMsvCollection();
        new RevertHandler(msvRepository, null);
        List<DBObject> dbObjectList = testMSVRepository.findAll();
        Assertions.assertEquals(0, dbObjectList.size());
    }


    @Test
    public void revertHandlerTestNonExistVer() {
        testMSVRepository.fillDummyVersion();
        Assertions.assertThrows(MSVException.class, () -> {
            new RevertHandler(msvRepository, "ASDA");
        });
    }


}
