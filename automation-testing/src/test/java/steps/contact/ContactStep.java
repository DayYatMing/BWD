package steps.contact;

import config.ConfigData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.contact.ContactPage;
import steps.BaseSteps;

import static hooks.Hooks.test;

public class ContactStep extends BaseSteps {

    @Given("user is on login page")
    public void onLoginPage() {
        driver().get(ConfigData.getBaseUrl());
        test.info("user is on login page");
    }

    @When("user navigates to and clicks Contacts menu")
    public void userNavigatesToAndClicksContactsMenu() {
        new ContactPage(driver()).navigateClickMenu();
        test.info("user navigates to and clicks Contacts menu");
    }

    @Then("user should be navigated to Contacts limited details page successfully")
    public void userShouldBeNavigatedToContactsLimitedPageSuccessfully() {
        new ContactPage(driver()).limitedPageShow();
        test.pass("user should be navigated to Contacts limited details page successfully");
    }

    @Then("user should be navigated to Contacts full details page successfully")
    public void userShouldBeNavigatedToContactsFullPageSuccessfully() {
        new ContactPage(driver()).fullPageShow();
        test.pass("user should be navigated to Contacts full details page successfully");
    }
}
