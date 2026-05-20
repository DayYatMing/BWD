package pages.auth;

import org.junit.Assert;
import service.OtpService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Screenshot;
import utils.TimeStamp;
import utils.WaitTime;

import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void login(String user, String pass) {
        By username = By.id("username");
        By password = By.id("password");
        By loginBtn = By.cssSelector("[data-cy='submit']");

        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);

        WaitTime.sleep(1);
        Screenshot.take(driver, "auth/login_" + TimeStamp.now());

        driver.findElement(loginBtn).click();
    }

    public void enterOtp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By otpField = By.cssSelector("input[formcontrolname='code']");
        By verifyBtn = By.cssSelector("button.btn.btn-success");

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(otpField)
        );

        String otp = OtpService.getOtp();
        input.clear();
        input.sendKeys(otp);

        WaitTime.sleep(1);
        Screenshot.take(driver, "auth/login_" + TimeStamp.now());

        wait.until(d -> d.findElement(verifyBtn).isEnabled());

        driver.findElement(verifyBtn).click();
    }

    public void loginSuccess(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("home-logged-message")
                )
        );
        String text = msg.getText();

        WaitTime.sleep(1);
        Screenshot.take(driver, "auth/login_" + TimeStamp.now());

        Assert.assertTrue(text.contains("logged in as user"));
    }
}
