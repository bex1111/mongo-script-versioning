package testutil;

import com.mongodb.DB;
import com.mongodb.Mongo;
import lombok.Data;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.net.UnknownHostException;


@Data
public class TestHelper {

    DB db;
    DB fakeDB;
    String fileLocation;


    public TestHelper() {
        initDB();
        initFileLocation();
    }

    private void initFileLocation() {
        String fileName = "0001_test.js";
        File file = new File(this.getClass().getResource("/" + fileName).getFile());
        fileLocation = file.getAbsolutePath().replace(File.separator + fileName, "");
    }

    private void initDB() {
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("MSVtest");
        } catch (UnknownHostException e) {
            Assertions.fail("Mongo innit problem: " + e.getMessage());
        }
    }


}
