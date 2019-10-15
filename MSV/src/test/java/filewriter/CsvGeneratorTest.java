package filewriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.MSVRepository;
import testutil.TestHelper;
import testutil.TestMSVRepository;
import util.FileHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;


public class CsvGeneratorTest {
    private TestHelper testHelper;
    private MSVRepository msvRepository;
    private TestMSVRepository testMSVRepository;

    public CsvGeneratorTest() {
        testHelper = new TestHelper();
        msvRepository = new MSVRepository(testHelper.getDb());
        testMSVRepository = new TestMSVRepository(testHelper.getDb());


    }

    private void deleteCsFiles() {

        FileHandler.getFileList(testHelper.getFileLocation()).stream().filter(x -> x.contains(".csv")).forEach(x -> {
            try {
                Files.deleteIfExists(Paths.get(testHelper.getFileLocation() + File.separator + x));
            } catch (Exception e) {
                Assertions.fail("Cannot delkete files");
            }
        });
    }

    @Test
    public void migrateHandlerTest1() {
        testMSVRepository.clearMsvCollection();
        testMSVRepository.fillDummyObject();
        new CsvGenerator(msvRepository, testHelper.getFileLocation());
        Optional<String> fileName = FileHandler.getFileList(testHelper.getFileLocation()).stream().filter(x -> x.contains(".csv")).findFirst();
        Assertions.assertTrue(fileName.isPresent());
        Assertions.assertEquals("full_name,description,version,installed_by,date,type,checksum,collection_name0002_test_test.json,asd,asd,asd,asd,Json,asd,asd0001_test.js,dummy,dummy,dummy,dummy,Js,dummy", FileHandler.readLineByLine(testHelper.getFileLocation(), fileName.get()));
        deleteCsFiles();
    }

}
