package net.dzakirin.userservice.logging;

public class Logger {
    private final org.slf4j.Logger sllogger;

    public Logger(org.slf4j.Logger sllogger) {
        this.sllogger = sllogger;
    }

    public void info(String msg) {
        sllogger.info(msg);
    }

    public void error(String s, String message) {
        sllogger.error("{} - {}", s, message);
    }

    // Add other logging methods (debug, warn, error) as needed
}
