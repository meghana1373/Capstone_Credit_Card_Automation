package com.project.tests.stepdefinitions;

import com.project.framework.base.DriverFactory;
import com.project.framework.pages.RegistrationPage;
import com.project.framework.utils.ExcelReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class RegistrationSteps {

    private WebDriver driver = DriverFactory.getDriver();
    private RegistrationPage registrationPage = new RegistrationPage(driver);
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("the user opens the application")
    public void openApp() {
        driver.get("https://demo.nopcommerce.com/");
    }

    @When("the user registers with valid details")
    public void registerValid() {
        driver.findElement(By.linkText("Register")).click();

        String randomEmail = "user" + System.currentTimeMillis() + "@example.com";
        String password = "Password123!";

        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        driver.findElement(By.id("Email")).sendKeys(randomEmail);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();

        // Save credentials for login use
        LoginSteps.registeredEmail = randomEmail;
        LoginSteps.registeredPassword = password;
    }

    @Then("registration should be successful")
    public void verifyRegistration() {
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".result")
        ));
        Assert.assertTrue(successMsg.getText().contains("Your registration completed"),
                "Expected success message not found! Got: " + successMsg.getText());
    }

    @When("the user registers with an existing email")
    public void registerExistingEmail() {
        driver.findElement(By.linkText("Register")).click();

        // Try to get from Excel first
        String existingEmail = ExcelReader.getCellData("LoginData", "Email", 1);
        String password = ExcelReader.getCellData("LoginData", "Password", 1);

        // Fallback if Excel is empty
        if (existingEmail == null || existingEmail.trim().isEmpty()) {
            existingEmail = LoginSteps.registeredEmail;
        }
        if (password == null || password.trim().isEmpty()) {
            password = (LoginSteps.registeredPassword != null && !LoginSteps.registeredPassword.isEmpty())
                    ? LoginSteps.registeredPassword : "Password123!";
        }

        if (existingEmail == null || existingEmail.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Existing email for duplicate registration is null/empty. Please check Excel or LoginSteps."
            );
        }

        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        driver.findElement(By.id("Email")).sendKeys(existingEmail.trim());
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();
    }

    @Then("the system should show an email already exists error")
    public void verifyEmailExists() {
        WebElement errorMsgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.message-error.validation-summary-errors")
        ));

        String errorMsg = errorMsgElement.getText();
        Assert.assertTrue(errorMsg.contains("The specified email already exists"),
                "Expected email already exists error, but got: " + errorMsg);
    }
}
