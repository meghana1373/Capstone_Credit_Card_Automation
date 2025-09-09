package com.project.tests.stepdefinitions;

import com.project.framework.base.DriverFactory;
import com.project.framework.pages.LoginPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginSteps {
    private WebDriver driver = DriverFactory.getDriver();
    private LoginPage loginPage;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Shared credentials set during registration
    public static String registeredEmail;
    public static String registeredPassword;

    @Given("a registered user exists")
    public void registeredUserExists() {
        // If RegistrationSteps already created a user in this run,
        // those details will be available here.
        if (registeredEmail == null || registeredPassword == null) {
            // fallback (ensure this exists in demo site, otherwise tests will fail)
            registeredEmail = "kothameghana036@gmail.com";
            registeredPassword = "meghana123";
        }
    }

    @When("the user logs in with valid credentials")
    public void loginValid() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        wait.until(ExpectedConditions.visibilityOf(loginPage.getEmailField())).sendKeys(registeredEmail);
        loginPage.getPasswordField().sendKeys(registeredPassword);
        loginPage.clickLogin();
    }

    @When("the user logs in with an incorrect password")
    public void loginInvalid() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();

        wait.until(ExpectedConditions.visibilityOf(loginPage.getEmailField())).sendKeys(registeredEmail);
        loginPage.getPasswordField().sendKeys("wrongpass"); // intentionally incorrect
        loginPage.clickLogin();
    }

    @Then("login should succeed")
    public void verifyLoginSuccess() {
        boolean isVisible = wait.until(driver -> loginPage.isLogoutVisible());
        Assert.assertTrue(isVisible, "Login failed! Logout link not visible.");
    }

    @Then("the system should show 'Login was unsuccessful'")
    public void verifyLoginFail() {
        String msg = wait.until(driver -> loginPage.getErrorMessage());
        Assert.assertTrue(msg.contains("Login was unsuccessful"),
                "Expected login failure message, but got: " + msg);
    }
}
