package com.project.framework.base;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.project.framework.utils.ScreenshotUtil;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class AppHooks {

    private WebDriver driver;

    @Before
    public void beforeScenario(Scenario scenario) {
        String browser = ConfigReader.get("browser");
        driver = DriverFactory.initDriver(browser);
        driver.get(ConfigReader.get("base.url"));

        ExtentCucumberAdapter.addTestStepLog("🚀 Starting Scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (driver != null) {
            try {
                // Always capture screenshot
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // Attach to Cucumber Report
                scenario.attach(screenshot, "image/png", scenario.getName());

                // Save screenshot path for Extent Report
                String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName());

                if (scenario.isFailed()) {
                    ExtentCucumberAdapter.addTestStepLog("❌ Scenario Failed: " + scenario.getName());
                    if (screenshotPath != null) {
                        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
                    }
                } else {
                    ExtentCucumberAdapter.addTestStepLog("✅ Scenario Passed: " + scenario.getName());
                    if (screenshotPath != null) {
                        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DriverFactory.quitDriver();
            }
        }
    }
}
