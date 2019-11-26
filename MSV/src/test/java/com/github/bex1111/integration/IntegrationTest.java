package com.github.bex1111.integration;

import com.github.bex1111.*;
import com.github.bex1111.testutil.FileUtil;
import com.github.bex1111.testutil.TestHelper;
import com.github.bex1111.testutil.TestMSVRepository;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.bex1111.testutil.TestHelper.TESTDBNAME;


public class IntegrationTest {

    private final static String LOCATION = "./src/test/resources";

    private void setDbName(Object reference) throws IllegalAccessException {
        FieldUtils.writeField(reference, "dbName", TESTDBNAME, true);
    }

    private void setDbAddress(Object reference) throws IllegalAccessException {
        FieldUtils.writeField(reference, "dbAddress", "localhost", true);
    }

    private void setDbPort(Object reference) throws IllegalAccessException {
        FieldUtils.writeField(reference, "dbPort", 27017, true);
    }

    private void setFileLocation(Object reference) throws IllegalAccessException {
        FieldUtils.writeField(reference, "fileLocation", LOCATION, true);
    }

    private void setOutputLocation(Object reference) throws IllegalAccessException {
        FieldUtils.writeField(reference, "outputLocation", LOCATION, true);
    }

    private void setRevertVersion(Object reference) throws IllegalAccessException {
        FieldUtils.writeField(reference, "revertVersion", "0.1.0", true);
    }


    private final TestHelper testHelper;
    private final TestMSVRepository testMSVRepository;

    public IntegrationTest() {
        this.testHelper = new TestHelper();
        this.testMSVRepository = new TestMSVRepository(testHelper.getDb());
    }


    @Test
    public void listingTest() throws MojoExecutionException, IllegalAccessException {
        Listing listing = new Listing();
        setDbName(listing);
        setDbAddress(listing);
        setDbPort(listing);
        listing.execute();
    }

    @Test
    public void migrateTest() throws IllegalAccessException {
        Migrate migrate = new Migrate();
        setDbName(migrate);
        setDbAddress(migrate);
        setDbPort(migrate);
        setFileLocation(migrate);
        Assertions.assertThrows(MojoExecutionException.class, () -> migrate.execute());
    }

    @Test
    public void revertTest() throws IllegalAccessException {
        Revert revert = new Revert();
        setDbName(revert);
        setDbAddress(revert);
        setDbPort(revert);
        setRevertVersion(revert);
        Assertions.assertThrows(MojoExecutionException.class, () -> revert.execute());
    }

    @Test
    public void toCsvTest() throws IllegalAccessException, MojoExecutionException {
        ToCsv tocsv = new ToCsv();
        setDbName(tocsv);
        setDbAddress(tocsv);
        setDbPort(tocsv);
        setOutputLocation(tocsv);
        tocsv.execute();
        FileUtil.deleteCsFiles(LOCATION);
    }

    @Test
    public void validateTest() throws IllegalAccessException, MojoExecutionException {
        testMSVRepository.clearMsvCollection();
        Validate validate = new Validate();
        setDbName(validate);
        setDbAddress(validate);
        setDbPort(validate);
        setFileLocation(validate);
        validate.execute();
    }

}
