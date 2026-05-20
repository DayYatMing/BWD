package utils;

public class WaitTime {

    public static void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (Exception ignored) {}
    }
}