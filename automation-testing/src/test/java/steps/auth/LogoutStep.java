package steps.auth;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.auth.LogoutPage;
import steps.BaseSteps;
import static hooks.Hooks.test;

public class LogoutStep extends BaseSteps {

    @Given("user is on logged-in page")
    public void onLoggedInPage() {
        new LogoutPage(driver()).onLoggedPage();
        test.info("user is on logged-in page");
    }

    @When("user navigates to and clicks logout button")
    public void userNavigatesToAndClicksLogoutButton() {
        new LogoutPage(driver()).navigateAndClickLogoutButton();
        test.info("user navigates to and clicks logout button");
    }

    @Then("user should be logged out successfully")
    public void userShouldBeLoggedOutSuccessfully() {
        new LogoutPage(driver()).logoutSuccess();
        test.pass("user should be logged out successfully");
    }
}
