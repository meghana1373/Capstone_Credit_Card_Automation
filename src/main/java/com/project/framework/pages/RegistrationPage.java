package com.project.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private final By registerLink = By.linkText("Register");
    private final By genderMale = By.id("gender-male");
    private final By genderFemale = By.id("gender-female");
    private final By firstName = By.id("FirstName");
    private final By lastName = By.id("LastName");
    private final By email = By.id("Email");
    private final By password = By.id("Password");
    private final By confirmPassword = By.id("ConfirmPassword");
    private final By registerButton = By.id("register-button");

    // Messages / errors
    private final By successMessage = By.cssSelector(".result");
    private final By validationSummaryError = By.cssSelector("div.message-error.validation-summary-errors");
    private final By fieldValidationError = By.cssSelector("span.field-validation-error");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Open registration page
    public void openRegistrationPage() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }

    // Gender selection
    public void selectMaleGender() {
        wait.until(ExpectedConditions.elementToBeClickable(genderMale)).click();
    }

    public void selectFemaleGender() {
        wait.until(ExpectedConditions.elementToBeClickable(genderFemale)).click();
    }

    // Input fields
    public void enterFirstName(String fname) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        el.clear();
        el.sendKeys(fname);
    }

    public void enterLastName(String lname) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        el.clear();
        el.sendKeys(lname);
    }

    public void enterEmail(String mail) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        el.clear();
        el.sendKeys(mail);
    }

    public void enterPassword(String pwd) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(password));
        el.clear();
        el.sendKeys(pwd);
    }

    public void enterConfirmPassword(String pwd) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword));
        el.clear();
        el.sendKeys(pwd);
    }

    // Click register
    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    // Convenience method to register a user
    public void registerUser(String fname, String lname, String mail, String pwd, boolean male) {
        openRegistrationPage();
        if (male) selectMaleGender();
        else selectFemaleGender();
        enterFirstName(fname);
        enterLastName(lname);
        enterEmail(mail);
        enterPassword(pwd);
        enterConfirmPassword(pwd);
        clickRegister();
    }

    // Expose WebElements for explicit waits in steps
    public WebElement getEmailField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(email));
    }

    public WebElement getPasswordField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(password));
    }

    public WebElement getConfirmPasswordField() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword));
    }

    // Messages / validation helpers
    public String getSuccessMessage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText().trim();
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return "";
        }
    }

    public String getValidationSummaryErrorText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(validationSummaryError)).getText().trim();
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return "";
        }
    }

    public String getFirstFieldValidationError() {
        try {
            return driver.findElement(fieldValidationError).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    // Boolean checks
    public boolean isRegistrationSuccessful() {
        String msg = getSuccessMessage();
        return msg != null && msg.contains("Your registration completed");
    }

    public boolean isDuplicateEmailErrorShown() {
        String val = getValidationSummaryErrorText();
        return val != null && val.contains("The specified email already exists");
    }
}
