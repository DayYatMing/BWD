package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStamp {
    private static final DateTimeFormatter DEFAULT_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");

    public static String now() {
        return LocalDateTime.now().format(DEFAULT_FORMAT);
    }

}
