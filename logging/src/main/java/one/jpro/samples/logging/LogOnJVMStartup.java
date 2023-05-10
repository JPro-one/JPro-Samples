package one.jpro.samples.logging;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Log config on JVM startup.
 *
 * @author Besmir Beqiri
 */
public class LogOnJVMStartup {

    private static final Logger logger = Logger.getLogger(LogOnJVMStartup.class.getName());

    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        logger.log(Level.INFO, "Some logging on JVM Startup!");
    }
}
