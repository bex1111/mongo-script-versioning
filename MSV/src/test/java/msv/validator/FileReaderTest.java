package msv.validator;

import msv.exception.MSVException;
import msv.migrate.MigrateHandler;
import msv.repository.MSVRepository;
import msv.testutil.TestHelper;
import msv.testutil.TestMSVRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;
import static msv.testutil.FileConst.*;

public class FileReaderTest {

    private final ValidatorFiles validatorFiles;
    private final TestHelper testHelper;
    private final MSVRepository msvRepository;
    private final TestMSVRepository testMSVRepository;

    public FileReaderTest() {
        this.testHelper = new TestHelper();
        this.msvRepository = new MSVRepository(testHelper.getDb());
        this.validatorFiles = new ValidatorFiles(msvRepository);
        this.testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    @Test
    public void fileReaderTwoCointan() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(), Arrays.asList(F0001, F0002), msvRepository);
        FileReader fileReader = new FileReader(testHelper.getFileLocation(), msvRepository);
        Assertions.assertEquals(0, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0001.getFileName())).collect(toList()).size());
        Assertions.assertEquals(0, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0002.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0003.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0004.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(FAAAA2.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(FAAAB2.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(FAAAA3.getFileName())).collect(toList()).size());
    }

    @Test
    public void fileReaderEmpty() {
        testMSVRepository.clearMsvCollection();
        FileReader fileReader = new FileReader(testHelper.getFileLocation(), msvRepository);
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0001.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0002.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0003.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(F0004.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(FAAAA2.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(FAAAB2.getFileName())).collect(toList()).size());
        Assertions.assertEquals(1, fileReader.getNewFileBaseDtos().stream().filter(x -> x.getFileName().equals(FAAAA3.getFileName())).collect(toList()).size());
    }

    @Test
    public void fileReaderValidationError() {
        testMSVRepository.clearMsvCollection();
        testMSVRepository.fillDummyObject();
        Assertions.assertThrows(MSVException.class, () -> new FileReader(testHelper.getFileLocation(), msvRepository));

    }

}
