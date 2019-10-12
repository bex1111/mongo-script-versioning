package filewriter;

import migrate.MigrateHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.MSVRepository;
import testutil.TestHelper;
import testutil.TestMSVRepository;
import util.FileHandler;

import java.io.File;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import static testutil.FileConst.F0001;
import static testutil.FileConst.F0002;


public class CsvGeneratorTest {
    private TestHelper testHelper;
    private MSVRepository msvRepository;
    private TestMSVRepository testMSVRepository;

    public CsvGeneratorTest() {
        try {
            testHelper = new TestHelper();
        } catch (UnknownHostException e) {
            Assertions.fail("No mongo");
        }
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
        new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(),
                Arrays.asList(F0001, F0002), msvRepository);
        new CsvGenerator(msvRepository, testHelper.getFileLocation());
        Optional<String> fileName = FileHandler.getFileList(testHelper.getFileLocation()).stream().filter(x -> x.contains(".csv")).findFirst();
        Assertions.assertTrue(fileName.isPresent());
        Assertions.assertEquals("full_name,description,version,installed_by,date,type,checksum,collection_name0001_test.js,test,0001,ba960,2019-10-12T19:30:54.793775800,Js,bb936ec9b3c5dfcd5dd233ef8a0901d3bec2d4fb9263bbf6532a3397a68076e00a5839035ecb2de79aa3fef316f438b7a5d58c9c30aec0494953fe40f768b8280002_test_test.json,test,0002,ba960,2019-10-12T19:30:54.816713100,Json,8237cf5105a41e96a025a1642ad66cf54b6239043792074b4041e8bdc470868cc9cda2ef57732f0a560e773bb4c669edbb0e85410d5e4153177169e9c86f198d,test", FileHandler.readLineByLine(testHelper.getFileLocation(), fileName.get()));
        deleteCsFiles();
    }

}
