import exception.MSVException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import reader.FileReader;

import java.io.File;

import static util.Logger.log;

@Mojo(name = "migrate")
public class Migrate extends AbstractMojo {

    @Parameter(required = true)
    private String dbName;

    @Parameter(required = true)
    private String dbPw;

    @Parameter(required = true)
    private String dbAddress;

    @Parameter(defaultValue = "${basedir}") //File.separator
    private String projectBuildDir;


    private FileReader fileReader;


    public void execute() throws MojoExecutionException {
        try {
            fileReader = new FileReader(projectBuildDir + File.separator + "src" + File.separator + "main" + File.separator + "resources");
            log().info("Project build dir: " + projectBuildDir);
            log().info("Hello, world.");
        } catch (MSVException e) {
            throw new MojoExecutionException(e.getMessage(), e.getCause());
        }
    }

}
