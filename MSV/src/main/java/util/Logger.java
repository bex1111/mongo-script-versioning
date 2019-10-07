package util;

import lombok.experimental.UtilityClass;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

@UtilityClass
public class Logger {

    private static Log log;

    public static Log log() {
        if (log == null) {
            log = new SystemStreamLog();
        }
        return log;
    }

}
