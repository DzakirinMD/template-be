package net.dzakirin.userservice.logging;

public interface Logger {

    boolean isTraceEnabled();

    void trace(String s);

    void trace(String s, Object o);

    void trace(String s, Object o, Object o1);

    void trace(String s, Object... objects);

    void trace(String s, Throwable throwable);

    boolean isDebugEnabled();

    void debug(String s);

    void debug(String s, Object o);

    void debug(String s, Object o, Object o1);

    void debug(String s, Object... objects);

    void debug(String s, Throwable throwable);

    void info(String s);

    void info(String s, Object o);

    void info(String s, Object... objects);

    void info(String s, Throwable throwable);

    void warn(String s);

    void warn(String s, Object o);

    void warn(String s, Object... objects);

    void warn(String s, Object o, Object o1);

    void warn(String s, Throwable throwable);

    void error(String s);

    void error(String s, Object... objects);

    void error(String s, Throwable throwable);

}
