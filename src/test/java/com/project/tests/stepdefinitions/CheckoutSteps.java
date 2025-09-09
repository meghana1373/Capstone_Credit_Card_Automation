package com.project.tests.stepdefinitions;

import com.project.framework.base.DriverFactory;
import io.cucumber.java.en.*;

import org.openqa.selenium.WebDriver;

public class CheckoutSteps {
    WebDriver driver = DriverFactory.getDriver();

    @When("a product is in the cart")
    public void productInCart() {
        // implement add to cart via POM
    }

    @When("the user proceeds to checkout and applies a valid credit card")
    public void checkoutWithValidCard() {
        // implement payment with valid card; pull data from excel
    }

    @When("the user attempts payment with an invalid card number")
    public void checkoutWithInvalidCard() {
        // negative payment attempt
    }

    @Then("payment should be processed and order confirmed")
    public void verifyPaymentSuccess() {}

    @Then("the payment should be rejected and an error shown")
    public void verifyPaymentRejected() {}
}
