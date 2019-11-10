package msv;

import com.mongodb.DB;
import com.mongodb.Mongo;
import msv.exception.MSVExceptionFactory;
import msv.filewriter.CsvGenerator;
import msv.listing.ListHandler;
import msv.migrate.MigrateHandler;
import msv.repository.MSVRepository;
import msv.revereter.RevertHandler;
import msv.validator.FileReader;

import java.net.UnknownHostException;

import static java.util.Objects.isNull;
import static msv.util.Logger.log;

public class Main {


    private DB db;
    private FileReader fileReader;
    private MSVRepository msvRepository;

    public Main(String dbName, String dbAddress, int dbPort, String dbPassword, String dbUsername) {
        initDB(dbName, dbAddress, dbPort, dbPassword, dbUsername);
        logLogo();
    }

    public void executeMigrate(String fileLocation) {
        fileReader = new FileReader(fileLocation, msvRepository);
        new MigrateHandler(fileLocation, db, fileReader.getNewFileBaseDtos(), msvRepository);
    }

    public void executeReverter(String revertVersion) {
        new RevertHandler(msvRepository, revertVersion);
    }

    public void executeValidation(String fileLocation) {
        new FileReader(fileLocation, msvRepository);
    }

    public void executeToCsv(String outputLocation) {
        new CsvGenerator(msvRepository, outputLocation);
    }

    public void executeListing() {
        new ListHandler(msvRepository);
    }

    private void initDB(String dbName, String dbAddress, int dbPort, String dbPassword, String dbUsername) {
        try {
            Mongo mongo = new Mongo(dbAddress, dbPort);
            db = mongo.getDB(dbName);
            if (!isNull(dbPassword) && !isNull(dbUsername) && !db.authenticate(dbUsername, dbPassword.toCharArray())) {
                throw MSVExceptionFactory.mongoAuthProblem();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        msvRepository = new MSVRepository(db);
    }

    private void logLogo() {
        log().info("");
        log().info(".##.....##..######..##.....##.");
        log().info(".###...###.##....##.##.....##.");
        log().info(".####.####.##.......##.....##.");
        log().info(".##.###.##..######..##.....##.");
        log().info(".##.....##.......##..##...##..");
        log().info(".##.....##.##....##...##.##...");
        log().info(".##.....##..######.....###....");
        log().info("");
    }

}