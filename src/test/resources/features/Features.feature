Feature: Create a new fulfilment order

  As a GE Ops user
  I would like to create a new fulfilment order
  So that I can send it to Deluxe for fulfilment

   @test
  Scenario: Navigate to new fulfilment order page from a licence and select all the Assets to Create an Order
    Given I open the new Fulfilment Page
    And I enter the licence id "120793"
    Then There should be some productions available
    When I select all the Asset Formats
    And I enter client "Sky UK" and profile "HD"
    Then the Fulfilment Order is COMPLETE

  @test
  Scenario: Production Smoke Test without clicking the Create Button
    Given I open the new Fulfilment Page
    And I enter the licence id "120793"
    Then There should be some productions available
    When I select all the Asset Formats
    And I enter client "Sky UK" and profile "HD"
    And I enter Required By Date "11/05/2019"

