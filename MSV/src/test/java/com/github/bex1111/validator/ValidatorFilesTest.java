package com.github.bex1111.validator;

import com.github.bex1111.exception.MSVException;
import com.github.bex1111.migrate.MigrateHandler;
import com.github.bex1111.repository.MSVRepository;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import com.github.bex1111.validator.dto.FileJsDto;
import com.github.bex1111.validator.dto.FileJsonDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.bex1111.testutil.FileConst.F0001;
import static com.github.bex1111.testutil.FileConst.F0002;

public class ValidatorFilesTest {

    private final ValidatorFiles validatorFiles;
    private final TestHelper testHelper;
    private final MSVRepository msvRepository;
    private final TestMSVRepository testMSVRepository;

    public ValidatorFilesTest() {
        this.testHelper = new TestHelper();
        this.msvRepository = new MSVRepository(testHelper.getDb());
        this.validatorFiles = new ValidatorFiles(msvRepository);
        this.testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }

    @Test
    public void fileJsonValidatorTestWrongLength() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.fileJsonValidator(new String[]{"dsf", "asd"}, "asd"));
    }

    @Test
    public void fileJsonValidatorTestContainSpace() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.fileJsonValidator(new String[]{"dsf", "sadas", "asd"}, "as d"));
    }

    @Test
    public void fileJsonValidatorTestSuccess() {
        FileJsonDto fileJsonDto = validatorFiles.fileJsonValidator(new String[]{"dsf", "sadas", "asd"}, "asd");
        Assertions.assertEquals("dsf", fileJsonDto.getVersion());
        Assertions.assertEquals("sadas", fileJsonDto.getDescription());
        Assertions.assertEquals("asd", fileJsonDto.getCollectionName());
        Assertions.assertEquals("asd", fileJsonDto.getFileName());
    }

    @Test
    public void fileJsValidatorTestWrongLength() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.fileJsValidator(new String[]{"dsf", "asd", "asd"}, "asd"));
    }

    @Test
    public void fileJsValidatorTestContainSpace() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.fileJsValidator(new String[]{"dsf", "asd"}, "as d"));
    }

    @Test
    public void fileJsValidatorTestSuccess() {
        FileJsDto fileJsDto = validatorFiles.fileJsValidator(new String[]{"dsf", "sadas"}, "asd");
        Assertions.assertEquals("dsf", fileJsDto.getVersion());
        Assertions.assertEquals("sadas", fileJsDto.getDescription());
        Assertions.assertEquals("asd", fileJsDto.getFileName());
    }

    @Test
    public void listUniqTestSuccess() {
        validatorFiles.listUniq(Arrays.asList("asd", "asdasfdds", "asdfsdgfdhfgjhfg", "asdfdfgfhfgjgh"));
    }

    @Test
    public void listUniqTestFail1() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.listUniq(Arrays.asList("asd", "asdasfdds", "asdfsdgfdhfgjhfg", "asdfdfgfhfgjgh", "asd")));
    }

    @Test
    public void listUniqTestFail2() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.listUniq(Arrays.asList("asd", "asdasfdds", "asdfsdgfdhfgjhfg", "asdfsdgfdhfgjhfg", "asdfdfgfhfgjgh", "asdd")));

    }

    @Test
    public void listUniqTestFail3() {
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.listUniq(Arrays.asList("1", "asd", "asdasfdds", "asdfsdgfdhfgjhfg", "asdfsdgfdhfgjhfg", "asdfdfgfhfgjgh", "asdd", "1")));
    }

    @Test
    public void validateFileTextFailJs() {
        testMSVRepository.clearMsvCollection();
        testMSVRepository.fillDummyObject();
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.validateFileText(testHelper.getFileLocation(), "0001_test.js"));
    }

    @Test
    public void validateFileTextFailJson() {
        testMSVRepository.clearMsvCollection();
        testMSVRepository.fillDummyObject();
        Assertions.assertThrows(MSVException.class, () -> validatorFiles.validateFileText(testHelper.getFileLocation(), "0002_test_test.json"));
    }

    @Test
    public void validateFileTextSuccessJs() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(), Arrays.asList(F0001), msvRepository);
        validatorFiles.validateFileText(testHelper.getFileLocation(), "0001_test.js");

    }

    @Test
    public void validateFileTextSuccessJson() {
        testMSVRepository.clearMsvCollection();
        new MigrateHandler(testHelper.getFileLocation(), testHelper.getDb(), Arrays.asList(F0002), msvRepository);
        validatorFiles.validateFileText(testHelper.getFileLocation(), "0002_test_test.json");
    }


}
