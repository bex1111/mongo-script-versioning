import com.mongodb.DB;
import com.mongodb.Mongo;
import exception.MSVExceptionFactory;
import filewriter.CsvGenerator;
import migrate.MigrateHandler;
import repository.MSVRepository;
import validator.FileReader;

import java.net.UnknownHostException;

import static java.util.Objects.isNull;

public class Main {


    private DB db;
    private FileReader fileReader;
    private MSVRepository msvRepository;

    public Main(String dbName, String dbAddress, int dbPort, String dbPassword, String dbUsername) {
        initDB(dbName, dbAddress, dbPort, dbPassword, dbUsername);
    }

    public void executeMigrate(String fileLocation) {
        fileReader = new FileReader(fileLocation, msvRepository);
        new MigrateHandler(fileLocation, db, fileReader.getNewFileBaseDtos(), msvRepository);

    }

    public void executeValidation(String fileLocation) {
        fileReader = new FileReader(fileLocation, msvRepository);
    }

    public void executeToCsv(String outputLocation) {
        new CsvGenerator(msvRepository, outputLocation);
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


}
