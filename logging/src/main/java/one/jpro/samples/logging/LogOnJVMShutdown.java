package one.jpro.samples.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a simple class that will be executed on JVM startup.
 *
 * @author Besmir Beqiri
 */
public class LogOnJVMShutdown {

    private static final Logger jullogger = Logger.getLogger(LogOnJVMShutdown.class.getName());

    public static void main(String[] args) {

        jullogger.log(Level.INFO, "JUL Logging on JVM Shutdown!");

        for (java.util.logging.Handler handler : Logger.getLogger("").getHandlers()) {
            handler.flush();
        }
        LogOnJVMStartup.julShutdownTrigger.complete(null);
        LogOnJVMStartup.loggerContext.stop();

    }
}