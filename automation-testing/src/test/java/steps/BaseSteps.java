package steps;

import driver.DriverFactory;
import org.openqa.selenium.WebDriver;

public class BaseSteps {

    protected WebDriver driver() {
        return DriverFactory.getDriver();
    }
}