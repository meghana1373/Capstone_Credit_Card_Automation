Feature: Positive flows for Credit Card Application and user journeys

  Background:
    Given the user opens the application

  Scenario: Successful registration with valid details
    When the user registers with valid details
    Then registration should be successful

  Scenario: Successful login with valid credentials
    Given a registered user exists
    When the user logs in with valid credentials
    Then login should succeed

  Scenario: Successful credit card payment at checkout
    Given a product is in the cart
    When the user proceeds to checkout and applies a valid credit card
    Then payment should be processed and order confirmed
