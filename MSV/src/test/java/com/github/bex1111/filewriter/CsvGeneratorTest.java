package com.github.bex1111.filewriter;

import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import com.github.bex1111.util.FileHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.github.bex1111.testutil.FileUtil.deleteCsFiles;


public class CsvGeneratorTest {
    private TestHelper testHelper;
    private MSVRepository msvRepository;
    private TestMSVRepository testMSVRepository;

    public CsvGeneratorTest() {
        testHelper = new TestHelper();
        msvRepository = new MSVRepository(testHelper.getDb());
        testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    @Test
    public void migrateHandlerTest1() {
        testMSVRepository.clearMsvCollection();
        testMSVRepository.fillDummyObject();
        new CsvGenerator(msvRepository, testHelper.getFileLocation());
        Optional<String> fileName = FileHandler.getFileList(testHelper.getFileLocation()).stream().filter(x -> x.contains(".csv")).findFirst();
        Assertions.assertTrue(fileName.isPresent());
        Assertions.assertEquals("full_name,description,version,installed_by,date,type,checksum,collection_name0002_test_test.json,asd,asd,asd,asd,Json,asd,asd0001_test.js,dummy,dummy,dummy,dummy,Js,dummy", FileHandler.readLineByLine(testHelper.getFileLocation(), fileName.get()));
        deleteCsFiles(testHelper);
    }

}
