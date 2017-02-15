package de.benedikt_werner.UNO;


public class Log {
    private static final LogLevel LOG_LEVEL = LogLevel.STANDARD;

    public static void debug(Object o) {
        if (LOG_LEVEL == LogLevel.DEBUG)
            System.out.println(o);
    }

    public static void info(Object o) {
        if (LOG_LEVEL != LogLevel.SILENT)
            System.out.println(o);
    }

    public static void print(Object o) {
        System.out.println(o);
    }

    public enum LogLevel {
        DEBUG, STANDARD, SILENT
    }
}
