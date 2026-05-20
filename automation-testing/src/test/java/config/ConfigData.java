package config;

public class ConfigData {

    public static String getBaseUrl() {
        return "http://10.10.1.4:9006";
    }

    public static String getUserLogin(String s){
        return switch (s.toLowerCase()) {
            case "admin" -> "admin";
            case "user" -> "user";
            default -> throw new RuntimeException("Unknown role: " + s);
        };
    }

    public static String getUserPassword() {
        return "JkKL7+su&=wybsLGjnY";
    }

    public static String getUserOtp() {
        return "123456";
    }

    public static String getScreenshotPath(){
        return "test-results/screenshots/";
    }

    public static String getTestReportPath(){
        return "test-results/test-reports/";
    }



}
