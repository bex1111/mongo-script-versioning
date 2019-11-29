package com.github.bex1111;

import com.github.bex1111.exception.MSVException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

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
    @Parameter(required = true)
    private String fileLocation;

    public void execute() throws MojoExecutionException {
        try {
            new Main(dbName, dbAddress, dbPort, dbPassword, dbUsername).executeMigrate(fileLocation);
        } catch (MSVException e) {
            throw new MojoExecutionException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
