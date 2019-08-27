package util;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {

    private static Logger logger;

    private LoggerUtil() {

    }

    public static Logger getLogger() {
        if (logger == null) {
            try {
                logger = Logger.getLogger("DebugLogger");

                logger.setUseParentHandlers(false);
                FileHandler fileHandler = new FileHandler("log.txt");
                fileHandler.setFormatter(new SimpleFormatter() {
                    private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                    @Override
                    public synchronized String format(LogRecord lr) {
                        return String.format(format,
                                new Date(lr.getMillis()),
                                lr.getLevel().getLocalizedName(),
                                lr.getMessage()
                        );
                    }
                });
                logger.addHandler(fileHandler);

                logger.info("Start logging");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
}
