package one.jpro.samples.logging;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.*;

/**
 * This is a simple class that will be executed on JVM startup.
 *
 * @author Besmir Beqiri
 * @author Florian Kirmaier
 */
public class LogOnJVMStartup {
    public static LoggerContext loggerContext = null;
    private static final Logger logger = Logger.getLogger(LogOnJVMStartup.class.getName());

    public static void main(String[] args) {
        // redirect j.u.l. logging to SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();

        String logbackFile = LogOnJVMStartup.class.getResource("/logback-sample.xml").getFile();
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
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

        synchronizeJULShutdown();

        logger.log(Level.INFO, "Logging on JVM Startup!");
    }

    public static void synchronizeJULShutdown() {
        // For reference: https://stackoverflow.com/questions/60687511/why-is-the-new-java-logger-failing-to-work-consistently-here
        Logger root = Logger.getLogger("");
        Handler[] handlers = root.getHandlers();

        for(Handler h : handlers) {
            root.removeHandler(h);
        }

        root.addHandler(new CleanerJoin());

        for(Handler h : handlers) {
            root.addHandler(h);
        }
    }


    public static CompletableFuture<Void> julShutdownTrigger = new CompletableFuture<>();
    static class CleanerJoin extends Handler {

        CleanerJoin() {
        }

        @Override
        public void close() {
            boolean interrupted = false;
            try {
                for(;;) {
                    try { //Could use LogManager to lookup timeout values and use a timed join.
                        julShutdownTrigger.get();
                        break;
                    } catch (ExecutionException e) {
                        reportError("Shutdown hook failed.", e, ErrorManager.CLOSE_FAILURE);
                        break;
                    } catch (InterruptedException retry) {
                        interrupted = true;
                    }
                }
            } finally {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        @Override
        public void flush() {
        }

        @Override
        public void publish(LogRecord r) {
            isLoggable(r);
        }
    }
}
