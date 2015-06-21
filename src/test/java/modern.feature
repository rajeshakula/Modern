Feature: Modern TestCaseSuite
  As a Tester i would like automate some functionalities

  @EmailSignUpTestCase
  Scenario Outline: EmailSignUp TestCase
    Given customer navigate to modern web page
    Then enter the emailaddress <Email>
    And click on sunscribe button
    Then Verfiy the <message>

    Examples: 
      | Email   | message                  |
      | valid   | Email address subscribed |
      | invalid | Email address exists     |

  @ProductPriceQuantity
  Scenario: Calculate sofa price based on Qunatity
    Given customer navigate to Modern page
    Then navigate to one of the tab
    And click on one of the sofa
    Then click on the Add to basket
    Then verfiy price vs quantity

  @ProductCatlogOrder
  Scenario: Sort out the Product order Low-High
    Given customer navigate to modern  page
    Then navigate to one of the productspage
    And select option price low to high
    Then Verfiy Sofas should sort out in an order price Low to High
