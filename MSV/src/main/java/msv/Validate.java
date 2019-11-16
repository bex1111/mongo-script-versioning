package msv;

import msv.exception.MSVException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "validate")
public class Validate extends AbstractMojo {

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


    public void execute() throws MojoExecutionException {
        try {
            new Main(dbName, dbAddress, dbPort, dbPassword, dbUsername).executeValidation(fileLocation);
        } catch (MSVException e) {
            throw new MojoExecutionException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
