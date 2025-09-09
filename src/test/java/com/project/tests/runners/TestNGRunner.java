package com.project.tests.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",       // Path to feature files
    glue = {
        "com.project.tests.stepdefinitions",        // Step definitions
        "com.project.framework.base"                // Hooks (AppHooks)
    },
    plugin = {
        "pretty",                                   // Console output
        "html:target/cucumber-reports/cucumber.html",  // HTML report
        "json:target/cucumber-reports/cucumber.json",  // JSON report
        "timeline:target/cucumber-reports/timeline",   // Timeline report
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // Extent report
    },
    monochrome = true,   // Removes unreadable characters in console
    publish = true,      // Publishes report to Cucumber cloud (optional)
    dryRun = false       // true = check if all steps have glue, false = execute tests
)
public class TestNGRunner extends AbstractTestNGCucumberTests {
}
