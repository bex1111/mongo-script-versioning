import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo( name = "migrate")
public class Migrate extends AbstractMojo{

    @Parameter(required =  true)
    private String dbName;

    @Parameter(required =  true)
    private String dbPw;

    @Parameter(required = true)
    private String dbAddress;

    @Parameter(defaultValue = "${project.build.directory}")
    private String projectBuildDir;



    public void execute() throws MojoExecutionException
    {

        getLog().info("Project build dir: " + projectBuildDir);
        getLog().info( "Hello, world." );
        throw new MojoExecutionException("Something went wrong");
    }

}
