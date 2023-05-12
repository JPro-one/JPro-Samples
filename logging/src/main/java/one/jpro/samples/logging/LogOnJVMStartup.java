package one.jpro.samples.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a simple class that will be executed on JVM startup.
 *
 * @author Besmir Beqiri
 */
public class LogOnJVMStartup {

    private static final Logger logger = Logger.getLogger(LogOnJVMStartup.class.getName());

    public static void main(String[] args) {
//        // redirect j.u.l. logging to SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        String logbackFile = LogOnJVMStartup.class.getResource("/logback-sample.xml").getFile();
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        loggerContext.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);

        SLF4JBridgeHandler.install();
        try {
            configurator.doConfigure(logbackFile);
        } catch (JoranException je) {
            je.printStackTrace();
            throw new RuntimeException(je.getMessage());
        }

        logger.log(Level.INFO, "Logging on JVM Startup!");
    }
}
