package net.dzakirin.userservice.logging;

public interface LoggerFactoryProvider {
    Logger getLogger(Class clazz);
}
