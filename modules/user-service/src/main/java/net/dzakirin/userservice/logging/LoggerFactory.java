package net.dzakirin.userservice.logging;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

@UtilityClass
public class LoggerFactory {

    @SuppressWarnings("rawtypes")
    public static Logger getLogger(Class clazz) {
        ServiceLoader<LoggerFactoryProvider> loggerServiceLoader = ServiceLoader.load(
                LoggerFactoryProvider.class);
        List<LoggerFactoryProvider> providers =
                StreamSupport.stream(loggerServiceLoader.spliterator(), false)
                        .toList();
        if (providers.isEmpty()) {
            throw new IllegalStateException("LoggerFactoryProvider implementation not found");
        } else if (providers.size() > 1) {
            throw new IllegalStateException("LoggerFactoryProvider has more than 1 implementation");
        } else {
            return providers.get(0).getLogger(clazz);
        }
    }
}
