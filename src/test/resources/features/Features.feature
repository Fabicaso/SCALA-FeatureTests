Feature: Create a new fulfilment order

  As a GE Ops user
  I would like to create a new fulfilment order
  So that I can send it to Deluxe for fulfilment

@test
  Scenario: Navigate to new fulfilment order page from a licence, Skip All ProductionIDs and then only select the First Asset to Create an Order
    Given I have navigated to the new fulfilment request page
    And I have entered licence id "120793"
    When I Skip All the Product IDs
   And I select only one asset for the firstProdID
  And I enter a new client profile "Sky UK HD"
  Then After I have clicked on the Create button the Fulfilment Order is COMPLETE

  @test
  Scenario: Navigate to new fulfilment order page from a licence and select all the Assets to Create an Order
    Given I open the new Fulfilment Page
    And I enter the licence id "120793"
    Then There should be some productions available
    When I select all the Asset Formats
    And I enter client profile "Sky Italia HD"
    And the Create button is clicked


