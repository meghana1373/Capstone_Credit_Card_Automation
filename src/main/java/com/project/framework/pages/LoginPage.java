package com.project.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By loginLink = By.linkText("Log in");
    private By email = By.id("Email");
    private By password = By.id("Password");
    private By loginBtn = By.cssSelector("button.login-button");
    private By logoutLink = By.linkText("Log out");
    private By errorMsg = By.cssSelector("div.message-error");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void openLoginPage() {
        driver.findElement(loginLink).click();
    }

    public void enterEmail(String e) {
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(e);
    }

    public void enterPassword(String p) {
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(p);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    // ✅ Expose WebElements for explicit waits in step definitions
    public WebElement getEmailField() {
        return driver.findElement(email);
    }

    public WebElement getPasswordField() {
        return driver.findElement(password);
    }

    // Assertions helpers
    public boolean isLogoutVisible() {
        return driver.findElements(logoutLink).size() > 0;
    }

    public String getErrorMessage() {
        return driver.findElement(errorMsg).getText();
    }
}
