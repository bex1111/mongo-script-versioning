package com.github.bex1111.validator;

import com.github.bex1111.exception.MSVException;
import com.github.bex1111.migrate.MigrateHandler;
import com.github.bex1111.testutil.FileUtil;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import com.github.bex1111.util.FileHandler;
import com.github.bex1111.validator.dto.FileJsonDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static com.github.bex1111.testutil.FileConst.*;
import static java.util.stream.Collectors.toList;

public class FileReaderTest {

    private final ValidatorFiles validatorFiles;
    private final TestHelper testHelper;
    private final TestMSVRepository testMSVRepository;

    public FileReaderTest() {
        this.testHelper = new TestHelper();
        this.validatorFiles = new ValidatorFiles(testHelper.getMsvRepository());
        this.testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    @Test
    public void fileReaderDeleteFile() {
        String filePath = testHelper.getFileLocation() + File.separator + "dummy_test_test.json";
        testMSVRepository.clearMsvCollection();
        FileHandler.writeString("{\n" +
                "  \"test\":\"test\"\n" +
                "}", filePath);
        new MigrateHandler(testHelper.getFileLocation(),
                Arrays.asList(F0001, F0002, FileJsonDto.builder().fileName("dummy_test_test.json").description("test").version("dummy").collectionName("test").build()), testHelper.getMsvRepository());
        FileUtil.deleteFile(filePath);
        Assertions.assertThrows(MSVException.class, () -> new FileReader(testHelper.getFileLocation(), testHelper.getMsvRepository()));
    }


    @Test
    public void fileReaderTwoCointan() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(), Arrays.asList(F0001, F0002), testHelper.getMsvRepository());
        FileReader fileReader = new FileReader(testHelper.getFileLocation(), testHelper.getMsvRepository());
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
        FileReader fileReader = new FileReader(testHelper.getFileLocation(), testHelper.getMsvRepository());
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
        Assertions.assertThrows(MSVException.class, () -> new FileReader(testHelper.getFileLocation(), testHelper.getMsvRepository()));

    }

}
