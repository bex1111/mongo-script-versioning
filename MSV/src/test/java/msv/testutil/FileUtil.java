package msv.testutil;

import lombok.experimental.UtilityClass;
import msv.util.FileHandler;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class FileUtil {


    public static void deleteCsFiles(TestHelper testHelper) {

        FileHandler.getFileList(testHelper.getFileLocation()).stream().filter(x -> x.contains(".csv")).forEach(x -> {
            try {
                Files.deleteIfExists(Paths.get(testHelper.getFileLocation() + File.separator + x));
            } catch (Exception e) {
                Assertions.fail("Cannot delkete files");
            }
        });
    }

    public static void deleteCsFiles(String location) {

        FileHandler.getFileList(location).stream().filter(x -> x.contains(".csv")).forEach(x -> {
            try {
                Files.deleteIfExists(Paths.get(location + File.separator + x));
            } catch (Exception e) {
                Assertions.fail("Cannot delkete files");
            }
        });
    }

}
