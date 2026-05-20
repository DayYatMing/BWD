package config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import utils.TimeStamp;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(ConfigData.getTestReportPath() + "ExtentReport_" + TimeStamp.now() + ".html");

            spark.config().setReportName("Automation Report");
            spark.config().setDocumentTitle("Test Results");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Tester", "Automation User");
        }

        return extent;
    }
}
