package hooks;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import config.ConfigData;
import config.ExtentManager;
import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import steps.BaseSteps;
import steps.auth.LoginStep;

public class Hooks extends BaseSteps {

    public static ExtentReports extent = ExtentManager.getExtentReports();
    public static ExtentTest test;

    @Before
    public void setup(Scenario scenario) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(">>>>>>>>>> Starting case >>>>>>>>>>");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        DriverFactory.initDriver();

        test = extent.createTest(scenario.getName());
        test.info("Scenario started");
    }

    @After
    public void teardown(Scenario scenario) {
        DriverFactory.quitDriver();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("<<<<<<<<<< Finished case <<<<<<<<<<");
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        if (scenario.isFailed()) {
            test.fail("Scenario FAILED: " + scenario.getName());
        } else {
            test.pass("Scenario PASSED");
        }
        extent.flush();
    }

    @Before("@requiresLogin")
    public void loginBeforeScenario() {
        driver().get(ConfigData.getBaseUrl());

        new LoginStep().login(ConfigData.getUserLogin("admin"));
        new LoginStep().enterOTP(ConfigData.getUserLogin("admin"));
    }
}