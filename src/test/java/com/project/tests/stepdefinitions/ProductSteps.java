package com.project.tests.stepdefinitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.project.framework.base.DriverFactory;
import com.project.framework.utils.ExcelReader;
import io.cucumber.java.en.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ProductSteps {

    private WebDriver driver = DriverFactory.getDriver();

    @When("I click login option in the home page")
    public void clickLoginOption() {
        driver.findElement(By.linkText("Log in")).click();
        ExtentCucumberAdapter.addTestStepLog("Clicked login option on home page");
    }

    @Then("I should navigate to the login page")
    public void verifyLoginPage() {
        Assert.assertTrue(driver.getTitle().contains("Login"), "Not on login page");
        ExtentCucumberAdapter.addTestStepLog("Navigated to login page");
    }

    @When("I enter user name and password")
    public void enterCredentials() {
        // Read data from Excel
        String username = ExcelReader.getCellData("LoginData", "Email", 1);
        String password = ExcelReader.getCellData("LoginData", "Password", 1);

        driver.findElement(By.id("Email")).sendKeys(username);
        driver.findElement(By.id("Password")).sendKeys(password);
        ExtentCucumberAdapter.addTestStepLog("Entered username and password from Excel");
    }

    @And("I click login button")
    public void clickLogin() {
        driver.findElement(By.cssSelector("button.login-button")).click();
        ExtentCucumberAdapter.addTestStepLog("Clicked login button");
    }

    @Then("I should navigate to the product page")
    public void verifyProductPage() {
        Assert.assertTrue(driver.findElement(By.linkText("Computers")).isDisplayed(),
                "Not navigated to product page");
        ExtentCucumberAdapter.addTestStepLog("Navigated to product page");
    }

    @When("I click desktop under computers menu")
    public void clickDesktopMenu() {
        driver.findElement(By.linkText("Computers")).click();
        driver.findElement(By.linkText("Desktops")).click();
        ExtentCucumberAdapter.addTestStepLog("Navigated to desktops page");
    }

    @And("I select any desktop under desktop page")
    public void selectDesktop() {
        driver.findElement(By.cssSelector("h2.product-title a")).click(); // first desktop
        ExtentCucumberAdapter.addTestStepLog("Selected a desktop product");
    }

    @And("I click add cart under desktop page")
    public void clickAddCartOnDesktop() {
        Assert.assertTrue(driver.findElement(By.cssSelector("div.product-name h1"))
                .getText().contains("Build your own computer"));
        ExtentCucumberAdapter.addTestStepLog("Verified product details page");
    }

    @Then("I should see the message as {string} title and price")
    public void verifyProductTitleAndPrice(String expectedTitle) {
        String actualTitle = driver.findElement(By.cssSelector("div.product-name h1")).getText();
        Assert.assertEquals(actualTitle, expectedTitle, "Product title mismatch");
        ExtentCucumberAdapter.addTestStepLog("Verified product title: " + actualTitle);
    }

    @When("I click on add cart option")
    public void clickAddCart() {
        // select dropdown options before adding
        driver.findElement(By.id("product_attribute_2")).sendKeys("4GB"); // RAM
        driver.findElement(By.id("product_attribute_3_6")).click();       // HDD radio
        driver.findElement(By.id("product_attribute_4_9")).click();       // OS radio

        driver.findElement(By.id("add-to-cart-button-1")).click();
        ExtentCucumberAdapter.addTestStepLog("Configured product options and clicked Add to Cart");
    }

    @Then("I see the message as {string}")
    public void verifyCartMessage(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Wait for notification bar to appear
        WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.bar-notification.success p")));

        String actualMessage = notification.getText().trim();

        Assert.assertTrue(actualMessage.contains(expectedMessage),
                "Expected: " + expectedMessage + " but got: " + actualMessage);

        ExtentCucumberAdapter.addTestStepLog("Verified success message: " + actualMessage);

        // Close the notification bar to continue test smoothly
        driver.findElement(By.cssSelector("div.bar-notification.success span.close")).click();
    }


    @When("I click shopping cart option")
    public void clickShoppingCart() {
        driver.findElement(By.linkText("Shopping cart")).click();
        ExtentCucumberAdapter.addTestStepLog("Clicked Shopping cart");
    }

    @Then("I see the message as {string} page title")
    public void verifyShoppingCartPage(String expectedTitle) {
        String actualTitle = driver.findElement(By.cssSelector("div.page-title h1")).getText();
        Assert.assertEquals(actualTitle, expectedTitle, "Shopping cart title mismatch");
        ExtentCucumberAdapter.addTestStepLog("Verified shopping cart page title: " + actualTitle);
    }
}
