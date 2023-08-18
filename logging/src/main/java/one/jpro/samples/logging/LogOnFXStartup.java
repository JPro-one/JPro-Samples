package one.jpro.samples.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Log config on FX startup.
 *
 * @author Besmir Beqiri
 */
public class LogOnFXStartup {

    private static final Logger logger = Logger.getLogger(LogOnFXStartup.class.getName());

    public static void main(String[] args) {
        logger.log(Level.INFO, "Some logging on JavaFX Application Startup!");
    }
}
