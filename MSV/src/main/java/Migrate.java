import migrate.FileReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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


    private final FileReader fileReader;

    public Migrate() {
        fileReader = new FileReader();
    }


    public void execute() throws MojoExecutionException {

        log().info("Project build dir: " + projectBuildDir);
        log().info("Hello, world.");
        fileReader.getFileList(projectBuildDir + File.separator + "src" + File.separator + "main" + File.separator + "resources");
        throw new MojoExecutionException("Something went wrong");
    }

}
