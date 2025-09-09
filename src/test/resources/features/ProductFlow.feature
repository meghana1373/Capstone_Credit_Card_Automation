Feature: Product purchase flow

  Background:
    Given the user opens the application

  Scenario: Login and add desktop product to cart
    When I click login option in the home page
    Then I should navigate to the login page
    When I enter user name and password
    And I click login button
    Then I should navigate to the product page
    When I click desktop under computers menu
    And I select any desktop under desktop page
    Then I should see the message as "Build your own computer" title and price
    When I click on add cart option
    Then I see the message as "The product has been added"
    When I click shopping cart option
    Then I see the message as "Shopping cart" page title
