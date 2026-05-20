package utils;

import config.ConfigData;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Screenshot {

    public static void take(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            File dest = new File(ConfigData.getScreenshotPath() + name + ".png");

            FileUtils.copyFile(src, dest);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save screenshot", e);
        }
    }
}