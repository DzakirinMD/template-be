package net.dzakirin.templatebe.logging;

public class Logger {
    private final org.slf4j.Logger logger;

    public Logger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    public void info(String msg) {
        logger.info(msg);
    }

    // Add other logging methods (debug, warn, error) as needed
}
