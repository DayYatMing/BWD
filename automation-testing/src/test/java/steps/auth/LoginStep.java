package steps.auth;

import config.ConfigData;
import io.cucumber.java.en.*;
import pages.auth.LoginPage;
import steps.BaseSteps;

import static hooks.Hooks.test;

public class LoginStep extends BaseSteps {

    @Given("{string} is on login page")
    public void openLoginPage(String role) {
        driver().get(ConfigData.getBaseUrl());
        test.info(role + " is on login page");
    }

    @When("{string} enters valid username and password")
    public void login(String role) {
        new LoginPage(driver()).login(
            ConfigData.getUserLogin(role),
            ConfigData.getUserPassword()
        );
        test.info(role + " enters valid username and password");
    }

    @And("{string} enters OTP")
    public void enterOTP(String role) {
        new LoginPage(driver()).enterOtp();
        test.info(role + " enters OTP");
    }

    @Then("{string} should be logged in successfully")
    public void verifyLogin(String role) {
        new LoginPage(driver()).loginSuccess();
        test.pass(role + " should be logged in successfully");
    }
}