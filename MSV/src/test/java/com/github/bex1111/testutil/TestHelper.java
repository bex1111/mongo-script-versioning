package com.github.bex1111.testutil;

import com.github.bex1111.repository.MSVRepository;
import com.mongodb.DB;
import com.mongodb.Mongo;
import lombok.Data;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.net.UnknownHostException;


@Data
public class TestHelper {

    public static String TESTDBNAME = "MSVtest";

    private DB db;
    private DB fakeDB;
    private String fileLocation;
    private MSVRepository msvRepository;


    public TestHelper() {
        initDB();
        initFileLocation();
        msvRepository = new MSVRepository(db);
    }

    private void initFileLocation() {
        String fileName = "0001_test.js";
        File file = new File(this.getClass().getResource("/" + fileName).getFile());
        fileLocation = file.getAbsolutePath().replace(File.separator + fileName, "");
    }

    private void initDB() {
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            db = mongo.getDB(TESTDBNAME);
        } catch (UnknownHostException e) {
            Assertions.fail("Mongo innit problem: " + e.getMessage());
        }
    }


}
