import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import exception.MSVException;
import exception.MSVExceptionFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import reader.FileReader;

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


    private FileReader fileReader;


    public void execute() throws MojoExecutionException {
        try {
            fileReader = new FileReader(fileLocation);
            initDb();
        } catch (MSVException e) {
            throw new MojoExecutionException(e.getMessage(), e.getCause());
        }
    }

    private void initDb() {
        try {

            Mongo mongo = new Mongo(dbAddress, dbPort);
            DB db = mongo.getDB(dbName);
            if (!isNull(dbPassword) && !isNull(dbUsername) && !db.authenticate(dbUsername, dbPassword.toCharArray())) {
                throw MSVExceptionFactory.mongoAuthProblem();
            }

//            DBCollection collection = db.getCollection("test");
//
//            // convert JSON to DBObject directly
//            DBObject dbObject = (DBObject) JSON
//                    .parse("[{'name':'mkyong', 'age':30},{'name':'asd', 'age':10}]");
//
//            collection.insert(dbObject);

            db.eval("db.products.insert( { item: \"card\", qty: 15 } );" +
                    "db.products.insert( { item: \"test\", qty: 10 } );");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

}
