package pages.contact;

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

public class ContactPage {

    WebDriver driver;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateClickMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By contactsMenu = By.cssSelector("a[href='/contact']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(contactsMenu));
        wait.until(ExpectedConditions.elementToBeClickable(contactsMenu)).click();
        WaitTime.sleep(1);
        Screenshot.take(driver, "contact/contact_" + TimeStamp.now());
    }

    public void limitedPageShow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("title")
                )
        );
        String text = msg.getText();

        WaitTime.sleep(1);
        Screenshot.take(driver, "contact/contact_" + TimeStamp.now());

        Assert.assertTrue(text.contains("Contact Details"));
    }

    public void fullPageShow() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("title")
                )
        );
        String text = msg.getText();

        WebElement msgExt = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("titleEscalation")
                )
        );
        String textExt = msgExt.getText();

        WaitTime.sleep(1);
        Screenshot.take(driver, "contact/contact_" + TimeStamp.now());

        Assert.assertTrue(text.contains("Contact Details"));
        Assert.assertTrue(textExt.contains("Escalation Details"));

    }
}
