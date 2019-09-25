import com.mongodb.DB;
import com.mongodb.Mongo;
import exception.MSVException;
import exception.MSVExceptionFactory;
import migrate.MigrateHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import reader.FileReader;
import repository.MSVRepository;

import java.net.UnknownHostException;

import static java.util.Objects.isNull;

@Mojo(name = "migrate")
public class Migrate extends AbstractMojo {

    @Parameter(required = true)
    private String dbName;
    @Parameter(required = true)
    private String dbAddress;
    @Parameter(required = true)
    private int dbPort;

    @Parameter
    private String dbPassword;

    @Parameter
    private String dbUsername;

    @Parameter
    private String fileLocation;

    private DB db;


    private FileReader fileReader;
    private MSVRepository msvRepository;


    public void execute() throws MojoExecutionException {
        try {
            initDB();
            msvRepository = new MSVRepository(db, fileLocation);
            fileReader = new FileReader(fileLocation);

            new MigrateHandler(fileLocation, db, fileReader.getFileBaseDtos(), msvRepository);
            msvRepository.findAll();
        } catch (MSVException e) {
            throw new MojoExecutionException(e.getMessage(), e.getCause());
        }
    }

    private void initDB() {
        try {
            Mongo mongo = new Mongo(dbAddress, dbPort);
            db = mongo.getDB(dbName);
            if (!isNull(dbPassword) && !isNull(dbUsername) && !db.authenticate(dbUsername, dbPassword.toCharArray())) {
                throw MSVExceptionFactory.mongoAuthProblem();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


}
