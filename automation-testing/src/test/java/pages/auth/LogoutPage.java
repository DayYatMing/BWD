package pages.auth;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Screenshot;
import utils.TimeStamp;
import utils.WaitTime;

import java.time.Duration;

public class LogoutPage {

    WebDriver driver;

    public LogoutPage(WebDriver driver) {this.driver = driver;}

    public void onLoggedPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("home-logged-message")
                )
        );
        String text = msg.getText();

        WaitTime.sleep(1);
        Screenshot.take(driver, "auth/logout_" + TimeStamp.now());

        Assert.assertTrue(text.contains("logged in as user"));
    }

    public void navigateAndClickLogoutButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By accountMenu = By.cssSelector("span[jhitranslate='global.menu.account.main']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountMenu));
        wait.until(ExpectedConditions.elementToBeClickable(accountMenu)).click();
        WaitTime.sleep(1);
        Screenshot.take(driver, "auth/logout_" + TimeStamp.now());

        By logoutBtn = By.xpath("//span[normalize-space()='Sign out']");
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();
    }

    public void logoutSuccess(){

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/login"));
        Screenshot.take(driver, "auth/logout_" + TimeStamp.now());

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
