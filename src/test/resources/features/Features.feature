Feature: Create a new fulfilment order

  As a GE Ops user
  I would like to create a new fulfilment order
  So that I can send it to Deluxe for fulfilment

@Ignore
  Scenario: Navigate to new fulfilment order page from a licence, Skip All ProductionIDs and then only select the First Asset to Create an Order
    Given I have navigated to the new fulfilment request page
    And I have entered licence id "123456789"
    When I Skip All the Asset and I click on the first Select Assets Button
   And Select all the Formats for each Assets
  Then After I have clicked on the Create button the Fulfilment Order is COMPLETE

  @test
  Scenario: Navigate to new fulfilment order page from a licence and select all the Assets to Create an Order
    Given I open the new Fulfilment Page
    And I enter the licence id "987654321"
    When I select all the Asset Formats
    And the Create button is clicked


