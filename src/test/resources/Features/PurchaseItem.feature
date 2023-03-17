Feature: Title of your feature

  Scenario: Valid Login
    Given User is on Launch Page
    When User login
    Then Should display Home Page
    
  Scenario Outline: Add items to cart
    Given User is on Launch Page
    When Add an item "<category>" and "<itemName>" to cart
    Then Items must be added to cart
    
  Examples:
  |category|itemName|
  |Phones|Iphone 6 32gb|
	|Monitors|Apple monitor 24|

  Scenario: Delete an Item
    Given User is on Launch Page
    When Delete an item from cart
    Then Items should be deleted in cart

  Scenario: Place Order	
    Given User is on Launch Page
    When Place Order
    Then Purchase Items