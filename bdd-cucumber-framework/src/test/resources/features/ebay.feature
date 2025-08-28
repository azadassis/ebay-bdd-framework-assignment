Feature: Ebay search

  Background:
    Given i am on the Ebay search page

  Scenario: Buying some Book
    When i search for "Book"
    Then the page title should start with "Book for sale | eBay"
    And add 4 to cart
    Then i will check the cart for the items added
